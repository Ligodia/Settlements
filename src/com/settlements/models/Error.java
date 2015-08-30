package com.settlements.models;

public enum Error
{
    LAND_ALREADY_CLAIMED("This land is already claimed by a settlement"),
    SETTLEMENT_ALREADY_EXISTS("A town already exists with that name"),
    PLAYER_ALREADY_IN_SETTLEMENT("That player is already in a settlement"),
    PLAYER_NOT_IN_SETTLEMENT("That player is not in the settlement"),
    SETTLEMENT_DOESNT_EXIST("A settlement of that name does not exist"),
    BLOCK_IS_NOT_ADJACENT("To claim a block it must "
            + "be adjacent to a block you already own"),
    NO_REMAINING_BLOCKS("Your settlement doesn't have enough "
            + "land allocation points to claim this block"),
    WILL_ISOLATE_BLOCK("Removing this block will isolate "
            + "another block from your land"),
    MINIMUM_SIZE("Removing this block will make the settlement smaller than" +
            "the minimum size"),
    BLOCK_ISNT_CLAIMED("Block must be claimed by your settlement to do this"),
    PLAYER_ALREADY_HAS_PERMISSIONS("The specified player already has permissions in this plot."),
    ALL_COLUMNS_ALREADY_IN_PLOT("The square of columns you've specified are all already in the plot."),
    NO_SUCH_CHILD_SETTLEMENT("The specified child does not exist in this settlement"),
    WAS_NOT_FOR_SALE("Settlement was not for sale to start with"),
    NO_NEGATIVES("This cannot be set to a negative number"),
    MUST_BE_MEMBER_OF_SETTLEMENT("You must a member of this settlement"),
    MUST_BE_STANDING_IN_SETTLEMENT("You must be standing within the " +
            "bounds of your settlement to do this"),
    COLUMN_ALREADY_ADDED("This column was already added to the settlement");

    private String message;

    Error(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
