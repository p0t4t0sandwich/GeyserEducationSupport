package ca.sperrer.geysereducationsupport;

/*
 * MIT License
 *
 * Copyright (c) 2021 GeyserReversion Developers
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.geyser.api.command.Command;
import org.geysermc.geyser.api.command.CommandExecutor;
import org.geysermc.geyser.api.command.CommandSource;

public class EducationCommand<T extends CommandSource> implements CommandExecutor<T> {
    private void showHelp(CommandSource sender) {
        sender.sendMessage("---- Education SubCommands ----");
        sender.sendMessage("&e/education new&f    - Create new Authorization URL");
        sender.sendMessage("&e/education confirm&f    - Confirm an Authorization Response");
        sender.sendMessage("");
        sender.sendMessage("Use '&enew&f' to generate a URL that you copy into your browser.");
        sender.sendMessage("This will allow you to log into your MCEE account. Once done you will have a white page with a URL both in");
        sender.sendMessage("its title as well as address bar. Copy the full address and provide it as a parameter to '&econfirm&f'.");
    }

    @Override
    public void execute(@NonNull T source, @NonNull Command command, @NonNull String[] args) {
        if (!source.isConsole()) {
            return;
        }

        if (args.length == 0) {
            showHelp(source);
            return;
        }

        switch (args[0].toLowerCase()) {
            case "new":
                source.sendMessage("Copy and paste the following into your web browser:");
                source.sendMessage("");
                source.sendMessage("   &e" + GeyserEducationSupport.getTokenManager().getNewAuthorizationUrl().toString());
                break;
            case "confirm":
                if (args.length < 2) {
                    source.sendMessage("Missing parameter");
                    return;
                }
                try {
                    GeyserEducationSupport.getTokenManager().createInitialToken(args[1]);
                } catch (TokenManager.TokenException e) {
                    source.sendMessage("Error: " + e.getMessage());
                }
                source.sendMessage("Successfully created new token");
                break;
            default:
                showHelp(source);
        }
    }
}
