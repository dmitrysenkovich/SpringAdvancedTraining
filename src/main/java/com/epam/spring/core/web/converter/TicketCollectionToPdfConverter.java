package com.epam.spring.core.web.converter;

import com.epam.spring.core.domain.Ticket;
import com.epam.spring.core.web.pdf.PdfCreator;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TicketCollectionToPdfConverter implements HttpMessageConverter<Collection<Ticket>> {
    private final PdfCreator pdfCreator;

    public TicketCollectionToPdfConverter(PdfCreator pdfCreator) {
        this.pdfCreator = pdfCreator;
    }

    @Override
    public boolean canRead(Class<?> aClass, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> aClass, MediaType mediaType) {
        return isCollectionOfTickets(aClass) && MediaType.APPLICATION_PDF.equals(mediaType);
    }

    /*
        I haven't found a better approach.
     */
    public boolean isCollectionOfTickets(Class<?> aClass) {
        try {
            Collection<Ticket> ticketCollection = (Collection<Ticket>) aClass.newInstance();

            return true;
        } catch (ClassCastException exc) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        } catch (InstantiationException e) {
            return false;
        }
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Arrays.asList(MediaType.APPLICATION_PDF);
    }

    @Override
    public Collection<Ticket> read(Class<? extends Collection<Ticket>> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(Collection<Ticket> tickets, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        byte[] pdfInBytes = pdfCreator.toPdf(tickets);
        httpOutputMessage.getBody().write(pdfInBytes);
    }
}
