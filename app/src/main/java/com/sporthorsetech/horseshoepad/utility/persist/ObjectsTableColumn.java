package com.sporthorsetech.horseshoepad.utility.persist;

enum ObjectsTableColumn implements DatabaseColumn
{
    id(SqliteType.TEXT),
    type(SqliteType.TEXT),
    json(SqliteType.TEXT),
    ts(SqliteType.INTEGER);

    private final SqliteType sqliteType;

    ObjectsTableColumn(SqliteType sqliteType)
    {
        this.sqliteType = sqliteType;
    }

    @Override
    public SqliteType getType()
    {
        return sqliteType;
    }

    @Override
    public String getNameAndType()
    {
        return name() + " " + getType();
    }
}
