package com.epam.spring.core.service.impl;

import com.epam.spring.core.dao.TicketRepository;
import com.epam.spring.core.domain.Ticket;
import com.epam.spring.core.service.ITicketService;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TicketServiceImpl implements ITicketService {
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {
        throw new UnsupportedOperationException("Method is not supported");
    }

    @Override
    public void remove(Ticket ticket) {
        throw new UnsupportedOperationException("Method is not supported");
    }

    @Override
    public Ticket getById(Long id) {
        return ticketRepository.findOne(id);
    }

    @Override
    public Collection<Ticket> getAll() {
        List<Ticket> targetCollection = new ArrayList<>();
        Iterable<Ticket> eventIterator = ticketRepository.findAll();
        CollectionUtils.addAll(targetCollection, eventIterator.iterator());
        return targetCollection;
    }
}
