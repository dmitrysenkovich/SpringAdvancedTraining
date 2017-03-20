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
import java.util.List;

public class TicketToPdfConverter implements HttpMessageConverter<Ticket> {
    private final PdfCreator pdfCreator;

    public TicketToPdfConverter(PdfCreator pdfCreator) {
        this.pdfCreator = pdfCreator;
    }

    @Override
    public boolean canRead(Class<?> aClass, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> aClass, MediaType mediaType) {
        return Ticket.class.equals(aClass) && MediaType.APPLICATION_PDF.equals(mediaType);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Arrays.asList(MediaType.APPLICATION_PDF);
    }

    @Override
    public Ticket read(Class<? extends Ticket> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(Ticket ticket, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        byte[] pdfInBytes = pdfCreator.toPdf(Arrays.asList(ticket));
        httpOutputMessage.getBody().write(pdfInBytes);
    }
}
