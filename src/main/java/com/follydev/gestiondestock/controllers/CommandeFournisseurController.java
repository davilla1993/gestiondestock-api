package com.follydev.gestiondestock.controllers;

import com.follydev.gestiondestock.controllers.api.CommandeFournisseurApi;
import com.follydev.gestiondestock.dto.CommandeFournisseurDto;
import com.follydev.gestiondestock.services.CommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommandeFournisseurController implements CommandeFournisseurApi {

    private CommandeFournisseurService commandeFournisseurSevice;

    @Autowired
    public CommandeFournisseurController(CommandeFournisseurService commandeFournisseurSevice) {
        this.commandeFournisseurSevice = commandeFournisseurSevice;
    }

    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto commandeFournisseurDto) {
        return commandeFournisseurSevice.save(commandeFournisseurDto);
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        return commandeFournisseurSevice.findById(id);
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        return commandeFournisseurSevice.findByCode(code);
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurSevice.findAll();
    }

    @Override
    public void delete(Integer id) {
        commandeFournisseurSevice.delete(id);
    }
}
