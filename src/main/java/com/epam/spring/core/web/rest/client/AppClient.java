package com.epam.spring.core.web.rest.client;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.Ticket;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.web.model.TicketInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class AppClient {
    private static final String LOGIN_FORM_TEMPLATE =
            "username=%s&password=%s&_csrf=%s";
    private static final String TICKETS_PRICE_QUERY_TEMPLATE =
            "eventId=%s&dateTime=%s&userId=%s&seats=%s";

    private String appUrl;
    private String userName;
    private String password;
    private String csrfToken;
    private String jSessionIdCookie;

    private RestTemplate restTemplate;

    public AppClient(String userName, String password) {
        this("http://localhost:8080", userName, password);
    }

    public AppClient(String appUrl, String userName, String password) {
        this.appUrl = appUrl;
        this.userName = userName;
        this.password = password;
        configureRestTemplate();
    }

    private void configureRestTemplate() {
        this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        restTemplate.setMessageConverters(Arrays.asList(mappingJackson2HttpMessageConverter,
                new StringHttpMessageConverter(), new ByteArrayHttpMessageConverter()));
        restTemplate.getInterceptors().add((request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.getAccept().add(MediaType.APPLICATION_JSON);

            return execution.execute(request, body);
        });
        restTemplate.getInterceptors().add((request, body, execution) -> {
            ClientHttpResponse clientHttpResponse = execution.execute(request, body);
            if (MediaType.TEXT_HTML.isCompatibleWith(clientHttpResponse.getHeaders().getContentType())) {
                throw new AuthorizationServiceException("Please login with correct credentials before using the client");
            }

            return clientHttpResponse;
        });
    }

    public void login() {
        ResponseEntity<String> csrfTokenResponse = restTemplate.getForEntity(appUrl + "/api/csrf", String.class);
        String csrfToken = csrfTokenResponse.getBody();

        this.csrfToken = csrfToken;
        restTemplate.getInterceptors().set(1, (request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.set("X-XSRF-TOKEN", this.csrfToken);
            ClientHttpResponse clientHttpResponse = execution.execute(request, body);
            if (clientHttpResponse.getHeaders().get("Set-Cookie") != null) {
                this.csrfToken = clientHttpResponse.getHeaders().get("Set-Cookie").get(0)
                        .replace("XSRF-TOKEN=", "").replace(";Path=/", "");
            }

            if (MediaType.TEXT_HTML.isCompatibleWith(clientHttpResponse.getHeaders().getContentType())) {
                throw new AuthorizationServiceException("Please login with correct credentials before using the client");
            }

            return clientHttpResponse;
        });

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.ALL));
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> httpEntity = new HttpEntity<>(String.format(LOGIN_FORM_TEMPLATE, userName, password, csrfToken),
                                                    httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(appUrl + "/login", httpEntity, String.class);
        if (response.getHeaders().getLocation().getPath().contains("/login")) {
            throw new AuthenticationCredentialsNotFoundException("Wrong authentication credentials");
        }

        jSessionIdCookie = response.getHeaders().get("Set-Cookie").get(1);
        restTemplate.getInterceptors().add(1, (request, body, execution) -> {
            HttpHeaders headers = request.getHeaders();
            headers.set("Set-Cookie", jSessionIdCookie);

            return execution.execute(request, body);
        });
    }

    public void logout() {
        restTemplate.postForObject(appUrl + "/logout", null, String.class);
    }

    public User user(Long id) {
        return restTemplate.getForObject(appUrl + "/api/user/" + id, User.class);
    }

    public List<User> users() {
        User[] users = restTemplate.getForObject(appUrl + "/api/user", User[].class);

        return Arrays.asList(users);
    }

    public User createUser(User user) {
        HttpEntity<User> httpEntity = new HttpEntity<>(user, null);

        return restTemplate.postForObject(appUrl + "/api/user", httpEntity, User.class);
    }

    public User updateUser(User user) {
        HttpEntity<User> httpEntity = new HttpEntity<>(user, null);

        return restTemplate.exchange(appUrl + "/api/user/" + user.getId(), HttpMethod.PUT, httpEntity, User.class).getBody();
    }

    public void deleteUser(Long id) {
        restTemplate.delete(appUrl + "/api/user/" + id);
    }

    public Event event(Long id) {
        return restTemplate.getForObject(appUrl + "/api/event/" + id, Event.class);
    }

    public List<Event> events() {
        Event[] events = restTemplate.getForObject(appUrl + "/api/event", Event[].class);

        return Arrays.asList(events);
    }

    public Event createEvent(Event event) {
        HttpEntity<Event> httpEntity = new HttpEntity<>(event, null);

        return restTemplate.postForObject(appUrl + "/api/event", httpEntity, Event.class);
    }

    public Event updateEvent(Event event) {
        HttpEntity<Event> httpEntity = new HttpEntity<>(event, null);

        return restTemplate.exchange(appUrl + "/api/event/" + event.getId(), HttpMethod.PUT, httpEntity, Event.class).getBody();
    }

    public void deleteEvent(Long id) {
        restTemplate.delete(appUrl + "/api/event/" + id);
    }

    public Ticket ticket(Long id) {
        return restTemplate.getForObject(appUrl + "/api/ticket/" + id, Ticket.class);
    }

    public List<Ticket> tickets() {
        Ticket[] tickets = restTemplate.getForObject(appUrl + "/api/ticket", Ticket[].class);

        return Arrays.asList(tickets);
    }

    public double ticketsPrice(Long eventId, Date dateTime, Long userId, Set<Long> seats) {
        return restTemplate.getForObject(appUrl + "/api/ticket/price?"
                + String.format(TICKETS_PRICE_QUERY_TEMPLATE, eventId, dateTime, userId, StringUtils.join(seats, ",")), Double.class);
    }

    public void book(List<TicketInfo> ticketInfos) {
        HttpEntity<List<TicketInfo>> httpEntity = new HttpEntity<>(ticketInfos, null);
        restTemplate.postForObject(appUrl + "/api/ticket/book", httpEntity, Void.class);
    }

    public Set<Ticket> purchasedTicketsForEvent(Long eventId, Date dateTime) {
        return restTemplate.exchange(appUrl + "/api/ticket/event?eventId=" + eventId + "&dateTime=" + dateTime,
                HttpMethod.GET, null, new ParameterizedTypeReference<Set<Ticket>>() {}).getBody();
    }

    public byte[] purchasedTicketsForEventInPdf(Long eventId, Date dateTime) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_PDF));
        HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(appUrl + "/api/ticket/event?eventId=" + eventId + "&dateTime=" + dateTime,
                HttpMethod.GET, httpEntity, byte[].class);

        return responseEntity.getBody();
    }

    public void refill(Long userId, Double amount) {
        restTemplate.put(appUrl + "/api/ticket/refill?userId=" + userId + "&amount=" + amount, null);
    }
}
