package demo3.the.flash.attribute;

import io.netty.util.AttributeKey;
import demo3.the.flash.session.Session;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
