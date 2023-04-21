package com.ccoins.coins.exceptions.constant;

public class ExceptionConstant {

    //LABELS
    public static final String ERROR_LABEL = "Error when trying to ";
    public static final String UNAUTHORIZED_LABEL = "User not authorized to ";
    public static final String LOGIN_WITH_ERROR_LABEL = ERROR_LABEL.concat("login with ");
    public static final String GET_ERROR_LABEL = ERROR_LABEL.concat("get ");
    public static final String GET_UNAUTHORIZED_LABEL = UNAUTHORIZED_LABEL.concat("get ");
    public static final String CREATE_NEW_ERROR_LABEL = ERROR_LABEL.concat("create new ");
    public static final String ACTIVE_UNACTIVE_ERROR_LABEL = ERROR_LABEL.concat("change state of ");
    public static final String GENERATE_ERROR_LABEL = ERROR_LABEL.concat("generate new ");
    public static final String ADD_ERROR_LABEL = ERROR_LABEL.concat("add ");
    public static final String COUNT_ERROR_LABEL = ERROR_LABEL.concat("count ");

    public static final String READ_ERROR_LABEL = ERROR_LABEL.concat("read ");

    public static final String CREATE_OR_REPLACE_ERROR_LABEL = ERROR_LABEL.concat("create or replace ");

    public static final String UPDATE_ERROR_LABEL = ERROR_LABEL.concat("update ");

    //ERRORS
    public static final String GENERIC_ERROR_CODE = "0001";
    public static final String GENERIC_ERROR = "Something went wrong! Check with your administrator";

    public static final String GOOGLE_ERROR_CODE = "0002";
    public static final String GOOGLE_ERROR = LOGIN_WITH_ERROR_LABEL.concat("Google");

    public static final String FACEBOOK_ERROR_CODE = "0003";
    public static final String FACEBOOK_ERROR = LOGIN_WITH_ERROR_LABEL.concat("Facebook");

    public static final String USERS_NEW_OWNER_ERROR_CODE = "0004";
    public static final String USERS_NEW_OWNER_ERROR = CREATE_NEW_ERROR_LABEL.concat("owner");

    public static final String USERS_GET_OWNER_BY_EMAIL_ERROR_CODE = "0005";
    public static final String USERS_GET_OWNER_BY_EMAIL_ERROR = GET_ERROR_LABEL.concat("owner");

    public static final String BARS_CREATE_OR_UPDATE_ERROR_CODE = "0006";
    public static final String BARS_CREATE_OR_UPDATE_ERROR = CREATE_OR_REPLACE_ERROR_LABEL.concat("bar");

    public static final String BARS_FIND_BY_ID_ERROR_CODE = "0007";
    public static final String BARS_FIND_BY_ID_ERROR = GET_ERROR_LABEL.concat("bar by id");

    public static final String BARS_FIND_BY_OWNER_ERROR_CODE = "0008";
    public static final String BARS_FIND_BY_OWNER_ERROR = GET_ERROR_LABEL.concat("bars by owner");

    public static final String USER_NOT_FOUND_ERROR_CODE = "0009";
    public static final String USER_NOT_FOUND_ERROR = "User not found.";

    public static final String TOKEN_NOT_FOUND_ERROR_CODE = "0010";
    public static final String TOKEN_NOT_FOUND_ERROR = GET_ERROR_LABEL.concat("Token");

    public static final String BARS_UNAUTHORIZED_ERROR_CODE = "0011";
    public static final String BARS_UNAUTHORIZED_ERROR = GET_UNAUTHORIZED_LABEL.concat("bar");

    public static final String JWT_EXPIRED_ERROR_CODE = "0012";
    public static final String JWT_EXPIRED_ERROR = "Expired token";

    public static final String TOKEN_VARIABLE_NOT_FOUND_ERROR_CODE = "0013";
    public static final String TOKEN_VARIABLE_NOT_FOUND_ERROR = READ_ERROR_LABEL.concat("variable from Token. Not found.");

    public static final String TABLE_CREATE_OR_UPDATE_ERROR_CODE = "0014";
    public static final String TABLE_CREATE_OR_UPDATE_ERROR = CREATE_OR_REPLACE_ERROR_LABEL.concat("table");

    public static final String TABLES_FIND_BY_ID_ERROR_CODE = "0015";
    public static final String TABLES_FIND_BY_ID_ERROR = GET_ERROR_LABEL.concat("table by id");

    public static final String TABLES_FIND_BY_BAR_ERROR_CODE = "0016";
    public static final String TABLES_FIND_BY_BAR_ERROR = GET_ERROR_LABEL.concat("tables by bar");

    public static final String TABLES_ACTIVE_ERROR_CODE = "0017";
    public static final String TABLES_ACTIVE_ERROR = ACTIVE_UNACTIVE_ERROR_LABEL.concat("table");

    public static final String BARS_ACTIVE_ERROR_CODE = "0018";
    public static final String BARS_ACTIVE_ERROR = ACTIVE_UNACTIVE_ERROR_LABEL.concat("bar");

    public static final String PRIZE_CREATE_OR_UPDATE_ERROR_CODE = "0019";
    public static final String PRIZE_CREATE_OR_UPDATE_ERROR = CREATE_NEW_ERROR_LABEL.concat("prize");

    public static final String PRIZE_FIND_BY_ID_ERROR_CODE = "0020";
    public static final String PRIZE_FIND_BY_ID_ERROR = GET_ERROR_LABEL.concat("prize by id");

    public static final String PRIZE_FIND_BY_BAR_ERROR_CODE = "0021";
    public static final String PRIZE_FIND_BY_BAR_ERROR = GET_ERROR_LABEL.concat("prize by bar");

    public static final String PRIZE_UPDATE_ACTIVE_ERROR_CODE = "0022";
    public static final String PRIZE_UPDATE_ACTIVE_ERROR = ACTIVE_UNACTIVE_ERROR_LABEL.concat("prize");

    public static final String GAME_CREATE_OR_UPDATE_ERROR_CODE = "0024";
    public static final String GAME_CREATE_OR_UPDATE_ERROR = CREATE_OR_REPLACE_ERROR_LABEL.concat("game");


    public static final String GAME_FIND_BY_ID_ERROR_CODE = "0025";
    public static final String GAME_FIND_BY_ID_ERROR = GET_ERROR_LABEL.concat("game by id");


    public static final String GAME_FIND_BY_OWNER_ERROR_CODE = "0026";
    public static final String GAME_FIND_BY_OWNER_ERROR = GET_ERROR_LABEL.concat("games by bar");

    public static final String GAME_ACTIVE_ERROR_CODE = "0027";
    public static final String GAME_ACTIVE_ERROR = ACTIVE_UNACTIVE_ERROR_LABEL.concat("game");


    public static final String GAME_FIND_GAME_TYPES_ERROR_CODE = "0028";
    public static final String GAME_FIND_GAME_TYPES_ERROR = GET_ERROR_LABEL.concat("games types");

    public static final String QR_CODE_GENERATION_ERROR_CODE = "0029";
    public static final String QR_CODE_GENERATION_ERROR = GENERATE_ERROR_LABEL.concat("qr code");

    public static final String USERS_NEW_CLIENT_ERROR_CODE = "0030";
    public static final String USERS_NEW_CLIENT_ERROR = CREATE_NEW_ERROR_LABEL.concat("client");

    public static final String USERS_GET_CLIENT_ERROR_CODE = "0031";
    public static final String USERS_GET_CLIENT_ERROR = GET_ERROR_LABEL.concat("client");

    public static final String PARTY_FIND_BY_TABLE_ERROR_CODE = "0032";
    public static final String PARTY_FIND_BY_TABLE_ERROR = GET_ERROR_LABEL.concat("party by table code");


    public static final String CLIENT_NOT_FOUND_ERROR_CODE = "0033";
    public static final String CLIENT_NOT_FOUND_ERROR = GET_ERROR_LABEL.concat("client");

    public static final String RANDOM_NAME_ERROR_CODE = "0034";
    public static final String RANDOM_NAME_ERROR = CREATE_NEW_ERROR_LABEL.concat("random name");

    public static final String PARTY_BY_BAR_ERROR_CODE = "0035";
    public static final String PARTY_BY_BAR_ERROR = GET_ERROR_LABEL.concat("party by bar");

    public static final String CREATE_PARTY_ERROR_CODE = "0036";
    public static final String CREATE_PARTY_ERROR = CREATE_NEW_ERROR_LABEL.concat("party");

    public static final String ADD_CLIENT_TO_PARTY_ERROR_CODE = "0037";
    public static final String ADD_CLIENT_TO_PARTY_ERROR = ADD_ERROR_LABEL.concat("client to party");

    public static final String UPDATE_CLIENT_NAME_ERROR_CODE = "0038";
    public static final String UPDATE_CLIENT_NAME_ERROR = UPDATE_ERROR_LABEL.concat("client nickname");

    public static final String COUNT_COINS_BY_PARTY_ERROR_CODE = "0039";
    public static final String COUNT_COINS_BY_PARTY_ERROR = COUNT_ERROR_LABEL.concat("coins by party");

    public static final String ALREADY_VOTED_ERROR_CODE = "0040";
    public static final String ALREADY_VOTED_ERROR = "User already vote on this match";

    public static final String NO_ENOUGH_COINS_ERROR_CODE = "0041";
    public static final String NO_ENOUGH_COINS_ERROR = "No enough coins to buy prize.";

    public static final String SAVE_COINS_ERROR_CODE = "0042";
    public static final String SAVE_COINS_ERROR = "Error trying to buy prize, retry later.";

    public static final String SONG_NOT_FOUND_ERROR_CODE = "0043";
    public static final String SONG_NOT_FOUND_ERROR = "Error trying to find a song.";

    public static final String COINS_REPORT_ERROR_CODE = "0044";
    public static final String COINS_REPORT_ERROR = "Error trying to get coins report";

    public static final String GET_COINS_STATES_ERROR_CODE = "0045";
    public static final String GET_COINS_STATES_ERROR = "Error trying to get coin states";

    public static final String DELIVER_PRIZE_OR_COINS_ERROR_CODE = "0046";


}
