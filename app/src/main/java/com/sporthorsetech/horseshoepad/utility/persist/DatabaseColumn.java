package com.sporthorsetech.horseshoepad.utility.persist;

/**
 * Represents a Database column.
 */
interface DatabaseColumn
{
    SqliteType getType();

    String getNameAndType();
}