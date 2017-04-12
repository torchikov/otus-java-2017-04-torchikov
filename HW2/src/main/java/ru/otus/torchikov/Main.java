package ru.otus.torchikov;

/**
 * Created by sergei on 09.04.17.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Measurer3000 measurer3000 = new Measurer3000();
        System.out.println("========================== OBJECTS ==============================");
        measurer3000.printObjectSize(ObjectTypeEnum.STRING_WITH_STRING_POOL);
        measurer3000.printObjectSize(ObjectTypeEnum.STRING_WITHOUT_STRING_POOL);
        measurer3000.printObjectSize(ObjectTypeEnum.OBJECT);
        measurer3000.printObjectSize(ObjectTypeEnum.INTEGER);
        System.out.println();
        System.out.println("========================== CONTAINERS ============================");
        measurer3000.printConteinerSize(0, ContainerTypeEnum.ARRAY, null);
        measurer3000.printConteinerSize(2, ContainerTypeEnum.ARRAY, ObjectTypeEnum.STRING_WITHOUT_STRING_POOL);

        measurer3000.printConteinerSize(0, ContainerTypeEnum.ARRAY_LIST, null);
        measurer3000.printConteinerSize(2, ContainerTypeEnum.ARRAY_LIST, ObjectTypeEnum.STRING_WITHOUT_STRING_POOL);

        measurer3000.printConteinerSize(0, ContainerTypeEnum.HASH_SET, null);
        measurer3000.printConteinerSize(2, ContainerTypeEnum.HASH_SET, ObjectTypeEnum.STRING_WITHOUT_STRING_POOL);

        measurer3000.printConteinerSize(0, ContainerTypeEnum.LINKED_LIST, null);
        measurer3000.printConteinerSize(2, ContainerTypeEnum.LINKED_LIST, ObjectTypeEnum.STRING_WITHOUT_STRING_POOL);

    }
}
