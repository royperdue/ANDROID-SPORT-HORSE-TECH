package com.sporthorsetech.horseshoepad.utility.persist;

enum TagsTableColumn implements DatabaseColumn
{
    tag(SqliteType.TEXT),
    value(SqliteType.TEXT),
    id(SqliteType.TEXT),
    type(SqliteType.TEXT);

    private final SqliteType sqliteType;

    private TagsTableColumn(SqliteType sqliteType)
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
