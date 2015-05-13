/**
 * Created by WeiChen on 2014/11/19.
 */

//object Chill {
//    def main(args: Array[String]) {
//        val items = List.fill(3000)(Create.randomWeaponAccessory())
//
//        val instantiator = new ScalaKryoInstantiator
//        instantiator.setRegistrationRequired(false)
//
//        val kryo = instantiator.newKryo()
//        val baos = new ByteArrayOutputStream
//        val output = new Output(baos, 4096)
//        kryo.writeObject(output, items)
//
//        val input = new Input(baos.toByteArray)
//        val deser = kryo.readObject(input, classOf[List[WeaponAccessory]])
//        assert(deser.size == 3000)
//    }
//}
