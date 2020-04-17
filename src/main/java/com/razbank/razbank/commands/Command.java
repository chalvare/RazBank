package com.razbank.razbank.commands;

import com.razbank.razbank.exceptions.RazBankException;

public interface Command {
    void execute() throws RazBankException;
}