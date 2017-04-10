package ru.otus.torchikov;

/**
 * Created by sergei on 09.04.17.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int repeatCount = 20; //Кол-во итераций
        final int elementCount = 100_000; //Кол-во элементов
        Measurer3000 measurer3000 = new Measurer3000(elementCount);
        Object[] array = new Object[elementCount];

        for (int i = 0; i < repeatCount; i++) {
            measurer3000.startMeasure();
            for (int j = 0; j < elementCount; j++) {
//                Подставить объект
//                Как ни пытался, в JDK 8 на пустую строку выдает 24 байта
//                40 выдает только при new String(new char[0]), а в JDK 7 сразу на пустую строку 40 байт
                array[j] = new String("");
            }
            measurer3000.stopMeasure();
            if (i == 0) { //Одного раза достаточно
                measurer3000.setClazz(array[0].getClass());
            }
            array = new Object[elementCount];
        }

        System.out.println("Size of " + measurer3000.getClazz() + " " + measurer3000.getResult() + " bytes");
//        Также открыл интересную вещь, если закоментировать 22-24 и 28 строку и раскоментировать 37,
//        то будет выдавать интересный результат, у меня -4 на любой обьект. Причем если запускать в дебаге
//        то все норм. Получается если запускаешь в IDEA в нормальном режиме (не дебаг) и не используешь нигде
//        ссылку на массив, то jvm каким-то образом видимо удаляет этот массив при GC. Другого обьяснения я не нашел.
//        Возможно jvm анализирует, что хоть ссылка есть на стеке, но ты ничего не читаешь из нее, то можно удалить,
//        а в дебаге такой анализ не делается
//        Баду рад если подскажете почему так.

//        System.out.println("Size = " + measurer3000.getResult());
    }
}
