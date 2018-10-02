package examples;

import java.io.*;

public class ReadWriteProtobufMain {

    public static void main(String[] params) throws IOException {
        AddressBookProtos.Person person = AddressBookProtos.Person.newBuilder()
                .setEmail("email@com")
                .setName("myname")
                .setId(123)
                .addPhones(AddressBookProtos.Person.PhoneNumber.newBuilder()
                        .setType(AddressBookProtos.Person.PhoneType.HOME)
                        .setNumber("12345678")
                        .build())
                .build();


        try (PipedOutputStream out = new PipedOutputStream();
             PipedInputStream in = new PipedInputStream(out)) {
            System.out.println("write Person:\n" + person);
            person.writeTo(out);
            out.close();

            AddressBookProtos.Person readPerson = AddressBookProtos.Person.newBuilder().mergeFrom(in).build();
            System.out.println("read Person:\n" + readPerson);
        }

    }

}
