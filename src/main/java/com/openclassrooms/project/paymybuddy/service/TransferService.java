package com.openclassrooms.project.paymybuddy.service;

import com.openclassrooms.project.paymybuddy.repo.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TransferService
{
    private final TransferRepository transferRepository;

//    public Transfer findById( int id )
//    {
//        return transferRepository.findById( id );
//    }


}
