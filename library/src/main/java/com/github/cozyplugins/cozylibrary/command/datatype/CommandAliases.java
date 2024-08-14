package com.github.cozyplugins.cozylibrary.command.datatype;

import com.github.cozyplugins.cozylibrary.pool.DistinctPool;

/**
 * <h1>Represents a set of command aliases</h1>
 * Contains a distinct pool of names that will also execute the command.
 */
public class CommandAliases extends DistinctPool<String, CommandAliases> {
}