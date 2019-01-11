package demo2.flash.protocol.command;

/**
 * 指令
 */
public interface Command {
    //登录指令
    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;



    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;
}
