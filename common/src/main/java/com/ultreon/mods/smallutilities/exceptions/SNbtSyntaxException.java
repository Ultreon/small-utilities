package com.ultreon.mods.smallutilities.exceptions;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class SNbtSyntaxException extends Throwable {
    private final CommandSyntaxException commandSyntaxException;

    public SNbtSyntaxException(final CommandSyntaxException commandSyntaxException) {
        super(commandSyntaxException.getMessage(), commandSyntaxException.getCause());
        this.commandSyntaxException = commandSyntaxException;
    }

    public CommandSyntaxException getCommandSyntaxException() {
        return this.commandSyntaxException;
    }

    @Override
    public String getLocalizedMessage() {
        return this.commandSyntaxException.getLocalizedMessage();
    }
}
