package com.example.spbmarks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "25ee596f7616e818f80f401ef0d3a2d6";
    public static String lat = "59.934178";
    public static String lon = "30.317944";
    public static String unit = "metric";
    public static String lang;
    private TextView temperature, about;
    private ImageView icon;
    private ImageButton buttonRu, buttonEn;
    private static int userId;

    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS sights (id INTEGER, image BLOB, sightName TEXT, metro TEXT, location TEXT, stared BOOLEAN, dateOfBuild TEXT, discription TEXT, architect TEXT, latitude REAL, longitude REAL, website TEXT, type INTEGER, openTime INTEGER, closeTime INTEGER, price INTEGER, priceForKids INTEGER, UNIQUE(id), FOREIGN KEY (type) REFERENCES types(type_id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS sights_en (id INTEGER, image BLOB, sightName TEXT, metro TEXT, location TEXT, stared BOOLEAN, dateOfBuild TEXT, discription TEXT, architect TEXT, latitude REAL, longitude REAL, website TEXT, type INTEGER, openTime INTEGER, closeTime INTEGER, price INTEGER, priceForKids INTEGER, UNIQUE(id), FOREIGN KEY (type) REFERENCES types(type_id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS types (type_id INTEGER NOT NULL, type_name TEXT NOT NULL, UNIQUE(type_id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS favourites(id INTEGER PRIMARY KEY AUTOINCREMENT, sight_id INTEGER NOT NULL, user_id INTEGER NOT NULL, UNIQUE(id), FOREIGN KEY (user_id) REFERENCES users(id))");

        buttonRu =  findViewById(R.id.imageButtonRu);
        buttonEn =  findViewById(R.id.imageButtonEn);
        temperature = findViewById(R.id.textView);
        about = findViewById(R.id.textView3);;
        icon = findViewById(R.id.imageView3);

        userId = getIntent().getIntExtra("userId", 0);

        getCurrentData();

        int draw_1 = R.drawable.kazan;
        int draw_2 = R.drawable.isac;
        int draw_3 = R.drawable.hermit;
        int draw_4 = R.drawable.vsadnik;
        int draw_5 = R.drawable.spas;
        int draw_6 = R.drawable.zamok;
        int draw_7 = R.drawable.rusmus;
        int draw_8 = R.drawable.petrop;
        int draw_9 = R.drawable.kunts;
        int draw_10 = R.drawable.marink;

        //sights

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type, openTime, closeTime, price, priceForKids) VALUES (1, " + draw_1 + ", 'Казанский собор' , 'Невский проспект.', 'Казанская пл., 2', false, '1811 г.','Один из крупнейших храмов Санкт-Петербурга. " +
                "Построен для хранения чтимого списка чудотворной иконы Божией Матери Казанской. " +
                "После Отечественной войны 1812 года приобрёл значение памятника русской воинской славы. " +
                "В 1813 году здесь был похоронен полководец Михаил Кутузов и помещены ключи от взятых городов и другие военные трофеи. " +
                "В 1932 году превращён в Музей истории религии и атеизма. " +
                "С 1991 года — действующий храм, несколько лет сосуществовавший с экспозицией музея." +
                "С 2000 года — кафедральный собор Санкт-Петербургской епархии Русской православной церкви. " +
                "С 9 марта 2019 года настоятелем является митрополит Санкт-Петербургский и Ладожский Варсонофий.', 'Андрей Никифорович Воронихин', 59.9347713, 30.3248576, 'http://kazansky-spb.ru/', 1, '9:00', '19:45', 'Бесплатно', 'Бесплатно')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type, openTime, closeTime, price, priceForKids) VALUES (2, " + draw_2 + ", 'Исаакиевский собор' , 'Адмиралтейская.', 'Исаакиевская пл., 4', false, '1858 г.', 'Крупнейший православный храм Санкт-Петербурга. " +
                "Современное здание собора является четвёртым петербургским храмом в честь Исаакия Далматского, возведённым на месте собора, спроектированного Антонио Ринальди. " +
                "При возведении здания использовались новые для того времени строительные технологии, что повлияло на дальнейшее развитие архитектуры XIX и XX века. " +
                "Сооружение здания и работы по его оформлению продолжались с 1818 по 1858 год. " +
                "Исаакиевский собор считается самой поздней постройкой в стиле классицизма', 'Огюст Монферран', 59.933489, 30.3067315, 'http://cathedral.ru/', 1, '10:30', '18:00', '450 руб', '200 руб')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (3, " + draw_3 + ", 'Эрмитаж' , 'Адмиралтейская.', 'Дворцовая пл., 2', false, '1764 г.','Является одним из крупнейших художественных музеев в мире." +
                " Главный музейный комплекс включает в себя шесть связанных между собой зданий. Общая площадь помещений Государственного Эрмитажа составляет 233 345 м²." +
                "Свою историю музей начинал с коллекции произведений искусства, приобретённых в частном порядке российской императрицей Екатериной II в 1764 году." +
                "Первоначально это собрание размещалось в специальном дворцовом флигеле — Эрмитаже, откуда и закрепилось общее название будущего музея." +
                "В 1852 году из сильно разросшейся коллекции был сформирован и открыт для посещения публичный музей, расположившийся в специально для этого построенном здании Нового Эрмитажа" +
                "Коллекция музея насчитывает около трёх миллионов произведений искусства и памятников мировой культуры," +
                "собранных начиная с каменного века и до нашего столетия.', 'Бартоломео Растрелли', 59.9396108, 30.3149881, 'https://www.hermitagemuseum.org/', 2, '10:00', '20:00', '500 руб', 'Бесплатно')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (4, " + draw_4 + ", 'Медный всадник' , 'Адмиралтейская.', 'Сенатская пл.', false, '1782 г.','Памятник Петру I на Сенатской площади в Санкт-Петербурге.\n" +
                "Памятник изготовлен из бронзы. Название «медный» закрепилось за ним благодаря поэме А. С. Пушкина «Медный всадник»." +
                "Модель конной статуи Петра выполнена скульптором Этьеном Фальконе в 1768—1770 г." +
                "Голову статуи лепила ученица этого скульптора, Мари Анн Колло." +
                "Змею по замыслу Фальконе вылепил Фёдор Гордеев." +
                "Отливка статуи осуществлялась под руководством литейных дел мастера Екимова Василия Петровича и была закончена в 1778 году." +
                "Архитектурно-планировочные решения и общее руководство осуществлял Юрий Фельтен.', 'Этьен Морис Фальконе', 59.9363632, 30.3019047, 'https://ru.wikipedia.org/wiki/%D0%9C%D0%B5%D0%B4%D0%BD%D1%8B%D0%B9_%D0%B2%D1%81%D0%B0%D0%B4%D0%BD%D0%B8%D0%BA', 'sight', '00:00', '24:00', 'Бесплатно', 'Бесплатно')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (5, " + draw_5 + ", 'Спас на крови' , 'Невский проспект.', 'наб. канала Грибоедова, 2Б', false, '1907 г.','Православный мемориальный однопрестольный храм во имя Воскресения Христова; сооружён в память того,\n" +
                "что на этом месте 1 марта 1881 года в результате покушения был смертельно ранен император Александр II" +
                "(выражение на крови указывает на кровь царя). Храм был сооружён как памятник царю на средства, собранные по всей России." +
                "Храм был возведён по указу императора Александра III в 1883—1907 годах по совместному проекту архитектора Альфреда Парланда и архимандрита Игнатия," +
                "который впоследствии от строительства отошёл. Проект выполнен в русском стиле, несколько напоминает московский собор Покрова на Рву." +
                "Строительство длилось 24 года. 6 августа 1907 года собор был освящён.', 'Альфред Александрович Парланд', 59.9397094, 30.3287002, 'http://spas.spb.ru/', 4, '10:30', '18:00', '450 руб', '100 руб')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (6, " + draw_6 + ", 'Михайловский замок' , 'Гостиный двор.', 'Садовая ул., 2', false, '1800 г.','Бывший императорский дворец в центре Санкт-Петербурга.\n" +
                "На рубеже XVIII—XIX веков построенный как замок на воде по заказу императора Павла I и ставший местом его смерти." +
                "Общий замысел создания замка и первые эскизы его планировки принадлежали самому Павлу." +
                "Работа над проектом будущей резиденции началась ещё в 1784 году, в бытность его великим князем." +
                "В процессе проектирования, которое длилось почти 12 лет, он обращался к различным архитектурным образцам," +
                "увиденным им во время заграничного путешествия 1781—1782 годов. Одним из возможных мест возведения нового дворца называлась Гатчина." +
                "Это здание — крупнейший архитектурный памятник, завершающий собою историю петербургского зодчества XVIII века." +
                "Павел I был убит офицерами в Михайловском замке в собственной опочивальне в ночь на 12 марта 1801 года. ', 'Винченцо Бренна', 59.9395817,  30.3381481, 'https://www.rusmuseum.ru/mikhailovsky-castle/', 4, '10:00', '18:00', '450 руб', '200 руб')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (7, " + draw_7 + ", 'Русский музей' , 'Невский проспект.', 'Инженерная ул., 4', false, '1895 г.','Музей является крупнейшим собранием российского искусства в мире.\n" +
                "Коллекция берёт своё начало с произведений, поступивших к 1898 году из Академии художеств (122 картины)," +
                "Эрмитажа (80 картин), Зимнего дворца, пригородных дворцов — Гатчинского и Александровского (95 картин)," +
                "а также приобретённых в частных коллекциях. В частности, крупная коллекция портретной живописи (несколько десятков картин)" +
                "поступила от наследников князя А. Б. Лобанова-Ростовского, коллекция рисунков и акварелей — от княгини М. К. Тенишевой и других." +
                "К открытию Русского музея в собрании имелось 445 картин, 111 скульптур, 981 графический лист (рисунки, гравюры и акварели)," +
                "а также около 5000 памятников старины (иконы и изделия древнерусского декоративно-прикладного искусства)." +
                "На 1 января 2015 года собрание Русского музея составило 410 945 единиц хранения. В это число входят произведения живописи," +
                "графики, скульптуры, нумизматики, декоративно-прикладного и народного искусства, а также архивные материалы.', 'Карло Росси', 59.9380167, 30.3320391, 'https://www.rusmuseum.ru/', 2, '10:00', '18:00', '400 руб', '200 руб')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (8, " + draw_8 + ", 'Петропавловская крепость' , 'Горьковская.', 'Заячий остров', false, '1740 г.','Старейший памятник архитектуры Санкт-Петербурга, крепость I класса.\n" +
                "Расположена на Заячьем острове, в Санкт-Петербурге, историческое ядро города." +
                "Дата закладки крепости 27 мая 1703 года, является датой основания Санкт-Петербурга. Никогда не использовалась ни в одном сражении." +
                "С первой четверти XVIII века до начала 1920-х годов служила тюрьмой. С 1924 года является государственным музеем." +
                "С 1873 года с Нарышкина бастиона крепости ежедневно в 12 часов производится артиллерийский сигнальный выстрел (не осуществлялся с 1934 по 1953 год)." +
                "Является историческим символом города." +
                "Согласно Уставу Санкт-Петербурга, историческими символами города являются ангел на шпиле Петропавловского собора," +
                "кораблик на шпиле Адмиралтейства и памятник «Медный всадник».', 'Доменико Трезини', 59.9499811, 30.3157435, 'https://www.spbmuseum.ru/themuseum/museum_complex/peterpaul_fortress/', 4, '10:00', '16:00', '750 руб', '400 руб')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (9, " + draw_9 + ", 'Кунсткамера' , 'Адмиралтейская.', 'Университетская наб., 3', false, '1714 г.','Первый музей в России, учреждённый императором Петром I в Санкт-Петербурге.\n" +
                "Пётр I во время «великого посольства» в 1697—1698 годах осматривал крупные преуспевающие города Голландии и Англии." +
                "Увидел и заморские кабинеты «кунштов», то есть редкостей, чудес. На страницах дневника, который приказал вести Пётр," +
                "часто мелькает восклицание «зело дивно!». Есть запись и о новейшей науке анатомии: «Видел у доктора анатомию:" +
                "вся внутренность разнята разно, — сердце человеческое, лёгкое, почки… Жилы, которые в мозгу живут, — как нитки…»." +
                "Петра очень заинтересовали подобные новшества и царь, не скупясь, закупал целые коллекции и отдельные вещи: книги, приборы," +
                "инструменты, оружие, природные редкости. Эти предметы и легли в основу «государева Кабинета», а потом и Петровской Кунсткамеры," +
                "первого российского естественнонаучного музея. Здание Кунсткамеры с начала XVIII в. является символом Российской академии наук.', 'Георг Иоганн Маттарнови', 59.9413583, 30.3046383, 'https://www.kunstkamera.ru/', 2, '10:00', '18:00', '400 руб', '100 руб')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (10, " + draw_10 + ", 'Мариинский театр' , 'Садовая.', 'Театральная пл., 1', false, '1714 г.','Театр оперы и балета в Санкт-Петербурге, один из ведущих музыкальных театров России и мира.\n" +
                "Труппа, основанная ещё в XVIII веке, до революции функционировала под руководством дирекции Императорских театров." +
                "В 1784—1886 годах для балетных и оперных спектаклей использовалось преимущественно здание Большого театра" +
                "(в 1896 году перестроено архитектором В. В. Николя для размещения Санкт-Петербургской консерватории)," +
                "в 1859—1860 году там же, на Театральной площади, архитектором А. К. Кавосом было выстроено нынешнее здание театра," +
                "названное в честь императрицы Марии Александровны." +
                "Театральный комплекс включает в себя собственно основное здание на Театральной площади, концертный зал (с 2006 года и новый южный корпус с 2021 года)," +
                "вторую сцену на Крюковом канале (с 2013 года) и филиалы во Владивостоке (с 2016 года) и Владикавказе (с 2017).', 'Альберт Катеринович Кавос', 59.9258033,  30.2970917, 'https://www.mariinsky.ru/', 4, '10:00', '18:00', 'от 500 руб', 'от 500 руб')");

        //sights-en

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (1, " + draw_1 + ", 'Kazan Cathedral' , 'Nevsky Prospect.', 'Kazanskaya sq., 2', false, '1811 y.','One of the largest churches in St. Petersburg." +
                "It was built to store a revered list of the miraculous icon of the Mother of God of Kazan." +
                "After the Patriotic War of 1812, it acquired the significance of a monument of Russian military glory." +
                "In 1813, the commander Mikhail Kutuzov was buried here and the keys to the captured cities and other military trophies were placed." +
                "In 1932 it was turned into the Museum of the History of Religion and Atheism. " +
                "Since 1991, it has been an active temple, coexisting with the museum`s exposition for several years." +
                "Since 2000 - the Cathedral of the St. Petersburg diocese of the Russian Orthodox Church." +
                "Since March 9, 2019, the rector has been Metropolitan Varsonofy of St. Petersburg and Ladoga.', 'Andrei Nikiforovich Voronikhin', 59.9347713, 30.3248576, 'http://kazansky-spb.ru/', 1, '9:00', '19:45', 'Free', 'Free')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (2, " + draw_2 + ", 'Saint Isaac`s Cathedral' , 'Admiralteyskaya.', 'Isaakievskaya sq., 4', false, '1858 y.', 'The largest Orthodox church in St. Petersburg." +
                "The modern building of the cathedral is the fourth St. Petersburg church in honor of St. Isaac of Dalmatia, erected on the site of the cathedral, designed by Antonio Rinaldi. " +
                "During the construction of the building, construction technologies new for that time were used, which influenced the further development of architecture in the 19th and 20th centuries." +
                "The construction of the building and work on its design continued from 1818 to 1858. " +
                "St. Isaac`s Cathedral is considered the latest building in the style of classicism', 'Auguste Montferrand', 59.933489, 30.3067315, 'http://cathedral.ru/', 1, '10:30', '18:00', '450 rub', '200 rub')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (3, " + draw_3 + ", 'Hermitage' , 'Admiralteyskaya.', 'Palace Square, 2', false, '1764 y.','It is one of the largest art museums in the world." +
                "The main museum complex includes six interconnected buildings. The total area of \u200B\u200Bthe premises of the State Hermitage is 233,345 m²." +
                "The museum began its history with a collection of works of art purchased privately by the Russian Empress Catherine II in 1764." +
                "Initially, this collection was housed in a special palace wing - the Hermitage, from which the common name of the future museum was fixed." +
                "In 1852, from a greatly expanded collection, a public museum was formed and opened to the public, located in a specially built building of the New Hermitage" +
                "The museum`s collection includes about three million works of art and monuments of world culture" +
                "collected from the Stone Age to the present century.', 'Bartolomeo Rastrelli', 59.9396108, 30.3149881, 'https://www.hermitagemuseum.org/', 2, '10:00', '20:00', '500 rub', 'Free')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (4, " + draw_4 + ", 'Bronze Horseman' , 'Admiralteyskaya.', 'Senatskaya sq.', false, '1782 y.','Monument to Peter I on Senate Square in St. Petersburg." +
                "The monument is made of bronze. The name \"copper\" was assigned to it thanks to A.S. Pushkin`s poem \"The Bronze Horseman\"." +
                "The model of the equestrian statue of Peter was made by the sculptor Etienne Falconet in 1768-1770" +
                "The statue`s head was sculpted by the sculptor`s` apprentice, Marie Anne Collot." +
                "Fyodor Gordeev fashioned the snake according to Falcone`s` plan." +
                "The casting of the statue was carried out under the guidance of foundry master Ekimov Vasily Petrovich and was completed in 1778." +
                "Architectural and planning decisions and general management were carried out by Yuri Felten.', 'Etienne Maurice Falcone', 59.9363632, 30.3019047, 'https://ru.wikipedia.org/wiki/%D0%9C%D0%B5%D0%B4%D0%BD%D1%8B%D0%B9_%D0%B2%D1%81%D0%B0%D0%B4%D0%BD%D0%B8%D0%BA', 3, '00:00', '24:00', 'Free', 'Free')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (5, " + draw_5 + ", 'Savior on Spilled Blood' , 'Nevsky Prospect.', 'emb. Griboedov Canal, 2B', false, '1907 y.','Orthodox memorial single-altar church in the name of the Resurrection of Christ; built in memory of that" +
                "that at this place on March 1, 1881, Emperor Alexander II was mortally wounded as a result of an assassination attempt" +
                "(The expression on the blood indicates the blood of the tsar). The temple was built as a monument to the tsar with funds raised throughout Russia." +
                "The temple was erected by decree of Emperor Alexander III in 1883-1907 according to the joint project of the architect Alfred Parland and Archimandrite Ignatius," +
                "which later abandoned construction. The project is made in the Russian style, somewhat reminiscent of the Moscow Cathedral of the Intercession on the Moat." +
                "The construction lasted 24 years. On August 6, 1907, the cathedral was consecrated.', 'Alfred Alexandrovich Parland', 59.9397094, 30.3287002, 'http://spas.spb.ru/', 4, '10:30', '18:00', '450 rub', '100 rub')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (6, " + draw_6 + ", 'Mikhailovsky Castle' , 'Gostiny yard.', 'Sadovaya st., 2', false, '1800 y.','The former imperial palace in the center of St. Petersburg." +
                "At the turn of the 18th-19th centuries, it was built as a castle on the water by order of Emperor Paul I and became the place of his death." +
                "The general idea of \u200B\u200Bcreating the castle and the first sketches of its layout belonged to Pavel himself." +
                "Work on the project for the future residence began back in 1784, when he was Grand Duke." +
                "During the design process, which lasted almost 12 years, he turned to various architectural patterns," +
                "he saw during his foreign trip in 1781-1782. One of the possible places for the construction of a new palace was called Gatchina." +
                "This building is the largest architectural monument that completes the history of St. Petersburg architecture of the 18th century." +
                "Paul I was killed by officers in the Mikhailovsky Castle in his own bedchamber on the night of March 12, 1801. ', 'Vincenzo Brenna', 59.9395817,  30.3381481, 'https://www.rusmuseum.ru/mikhailovsky-castle/', 4, '10:00', '18:00', '450 rub', '200 rub')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (7, " + draw_7 + ", 'Russian Museum' , 'Nevsky Prospect.', 'Engineering street, 4', false, '1895 y.','The museum is the largest collection of Russian art in the world." +
                "The collection originates from works received by 1898 from the Academy of Arts (122 paintings)," +
                "The Hermitage (80 paintings), the Winter Palace, suburban palaces - Gatchina and Alexander (95 paintings), " +
                "as well as those acquired in private collections. In particular, a large collection of portraiture (several dozen paintings)" +
                "came from the heirs of Prince A. B. Lobanov-Rostovsky, a collection of drawings and watercolors - from Princess M. K. Tenisheva and others." +
                "By the opening of the Russian Museum, the collection had 445 paintings, 111 sculptures, 981 graphic sheets (drawings, engravings and watercolors)," +
                "as well as about 5000 ancient monuments (icons and products of ancient Russian arts and crafts)." +
                "As of January 1, 2015, the collection of the Russian Museum amounted to 410,945 items. This number includes paintings," +
                "graphics, sculpture, numismatics, arts and crafts and folk art, as well as archival materials.', 'Carlo Rossi', 59.9380167, 30.3320391, 'https://www.rusmuseum.ru/', 2, '10:00', '18:00', '400 rub', '200 rub')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (8, " + draw_8 + ", 'Peter-Pavel`s Fortress' , 'Gorkovskaya.', 'Hare Island', false, '1740 y.','The oldest architectural monument of St. Petersburg, a class I fortress." +
                "Located on Zayachy Island, in St. Petersburg, the historical core of the city." +
                "The date of foundation of the fortress, May 27, 1703, is the date of the founding of St. Petersburg. It has never been used in any battle." +
                "From the first quarter of the 18th century until the early 1920s, it served as a prison. Since 1924, it has been a state museum." +
                "Since 1873, an artillery signal shot has been fired from the Naryshkin bastion of the fortress every day at 12 oclock (it was not carried out from 1934 to 1953)." +
                "It is a historical symbol of the city." +
                "According to the Charter of St. Petersburg, the historical symbols of the city are the angel on the spire of the Peter and Paul Cathedral," +
                "A boat on the spire of the Admiralty and a monument to the Bronze Horseman', 'Domenico Trezzini', 59.9499811, 30.3157435, 'https://www.spbmuseum.ru/themuseum/museum_complex/peterpaul_fortress/', 4, '10:00', '16:00', '750 rub', '400 rub')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (9, " + draw_9 + ", 'Kunstkamera' , 'Admiralteyskaya.', 'Universitetskaya emb., 3', false, '1714 y.','The first museum in Russia, founded by Emperor Peter I in St. Petersburg." +
                "Peter I during the great embassy in 1697-1698 examined the large prosperous cities of Holland and England." +
                "I also saw the overseas cabinets of kunshtov, that is, rarities, miracles. On the pages of the diary that Peter ordered to keep," +
                "The exclamation very marvelous! often flashes. There is also a record about the latest science of anatomy: I saw the doctor`s anatomy " +
                "the whole inside is divided in different ways - the human heart, lung, kidneys... The veins that live in the brain are like threads..." +
                "Peter was very interested in such innovations and the tsar, without stint, bought entire collections and individual items books, instruments," +
                "tools, weapons, natural rarities. These items formed the basis of the sovereign`s Cabinet, and then the Petrovsky Kunstkamera," +
                "the first Russian natural science museum. The building of the Kunstkamera has been a symbol of the Russian Academy of Sciences since the beginning of the 18th century.', 'Georg Johann Mattarnovi', 59.9413583, 30.3046383, 'https://www.kunstkamera.ru/', 2, '10:00', '18:00', '400 rub', '100 rub')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (10, " + draw_10 + ", 'Mariinsky Theatre' , 'Sadovaya.', 'Theater Square, 1', false, '1714 y.','The Opera and Ballet Theater in St. Petersburg, one of the leading musical theaters in Russia and the world." +
                "The troupe, founded in the 18th century, before the revolution, functioned under the leadership of the directorate of the Imperial Theaters." +
                "In 1784-1886, the building of the Bolshoi Theater was mainly used for ballet and opera performances" +
                "(rebuilt in 1896 by the architect V. V. Nikola to accommodate the St. Petersburg Conservatory), " +
                "in 1859-1860, in the same place, on Theater Square, the current building of the theater was built by the architect A.K. Kavos, " +
                "named after Empress Maria Alexandrovna." +
                "The theater complex includes the main building itself on Theater Square, a concert hall (since 2006 and a new southern building since 2021)," +
                "the second stage on the Kryukov Canal (since 2013) and branches in Vladivostok (since 2016) and Vladikavkaz (since 2017).', 'Albert Katerinovich Kavos', 59.9258033,  30.2970917, 'https://www.mariinsky.ru/', 4, '10:00', '18:00', 'from 500 rub', 'from 500 rub')");


        db.execSQL("INSERT OR IGNORE INTO types VALUES (1, 'cathedral')");
        db.execSQL("INSERT OR IGNORE INTO types VALUES (2, 'museum')");
        db.execSQL("INSERT OR IGNORE INTO types VALUES (3, 'sight')");
        db.execSQL("INSERT OR IGNORE INTO types VALUES (4, 'other')");
    }

    private void setAppLocale(String localeCode)
    {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            conf.locale = new Locale(localeCode.toLowerCase());

        }
        res.updateConfiguration(conf, dm);
    }

    public void exit (View v)
    {
        finishAffinity();
        System.exit(0);
    }

    public void goToSights (View view)
    {
        Intent intent = new Intent(this, SightType.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
        finish();
    }

    public void aboutUs (View view)
    {
        Intent intent = new Intent(this, AboutAppActivity.class);
        startActivity(intent);
    }

    public void onMap (View view)
    {
        Intent intent = new Intent(this, SightOnMapActivity.class);
        startActivity(intent);
    }

    public void languageEn(View view)
    {
        setAppLocale("en");
        startActivity(getIntent());
    }

    public void languageRu(View view)
    {
        setAppLocale("ru");
        startActivity(getIntent());
    }

    public void userExit(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    void getCurrentData() {

        String currentLang = getString(R.string.language);
        String isRusLanguageSelected = "ru";

        if(currentLang.equals(isRusLanguageSelected)) {
            lang = "ru";
        }

        else
        {
            lang = "en";
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = service.getCurrentWeatherData(lat, lon,lang, AppId, unit);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    float temp = weatherResponse.main.temp;
                    int roundTemp = Math.round(temp);

                    temperature.setText("" + roundTemp + "°C");
                    about.setText("" + weatherResponse.getWeatherList().get(0).description);

                    String w_icon_num = weatherResponse.getWeatherList().get(0).icon;
                    String new_icon_num = "w_icon_" + w_icon_num;
                    int res = getResources().getIdentifier(new_icon_num, "drawable", getPackageName());
                    icon.setImageResource(res);

                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                temperature.setText(t.getMessage());
            }
        });
    }
}