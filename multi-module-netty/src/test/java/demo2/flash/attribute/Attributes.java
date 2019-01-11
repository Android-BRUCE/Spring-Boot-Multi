package demo2.flash.attribute;

import io.netty.util.AttributeKey;
import demo2.flash.session.Session;

public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
