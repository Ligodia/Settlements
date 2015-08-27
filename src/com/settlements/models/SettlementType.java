package com.settlements.models;

public enum SettlementType
{

    DWELLING(1, null), HAMLET(3, null), VILLAGE(7, Rank.CHIEF), TOWN(20, Rank.MAYOR), CITY(
        55, Rank.MAYOR), METROPOLIS(148, Rank.MAYOR), COUNTY(403, Rank.GOVERNOR), COUNTRY(
        1097, Rank.PRESIDENT), EMPIRE(2981, Rank.EMPEROR);

    private int sizeCoefficient;
    private Rank rank;

    SettlementType(int sizeCoefficient, Rank rank)
    {
        this.setSizeCoefficient(sizeCoefficient);
        this.rank = rank;
    }

    public int getSizeCo()
    {
        return sizeCoefficient;
    }

    public Rank getLeaderRank()
    {
        return rank;
    }

    public void setSizeCoefficient(int sizeCoefficient)
    {
        this.sizeCoefficient = sizeCoefficient;
    }
}
