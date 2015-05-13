package mypackage
import com.esotericsoftware.kryo.Kryo
import org.apache.spark.serializer.KryoRegistrator


/**
 * Created by WeiChen on 2014/11/13.
 */

class MyRegistrator extends KryoRegistrator {
    override def registerClasses(kryo: Kryo) {
        kryo.register(classOf[Models])
    }
}
