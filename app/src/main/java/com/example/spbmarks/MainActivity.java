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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS sights (id INTEGER, image BLOB, sightName TEXT, metro TEXT, location TEXT, stared BOOLEAN, dateOfBuild TEXT, discription TEXT, architect TEXT, latitude REAL, longitude REAL, website TEXT, type INTEGER, openTime INTEGER, closeTime INTEGER, price INTEGER, priceForKids INTEGER, UNIQUE(id), FOREIGN KEY (type) REFERENCES types(type_id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS sights_en (id INTEGER, image BLOB, sightName TEXT, metro TEXT, location TEXT, stared BOOLEAN, dateOfBuild TEXT, discription TEXT, architect TEXT, latitude REAL, longitude REAL, website TEXT, type INTEGER, openTime INTEGER, closeTime INTEGER, price INTEGER, priceForKids INTEGER, UNIQUE(id), FOREIGN KEY (type) REFERENCES types(type_id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS types (type_id INTEGER NOT NULL, type_name TEXT NOT NULL, UNIQUE(type_id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS favourites(id INTEGER PRIMARY KEY AUTOINCREMENT, sight_id INTEGER NOT NULL, user_id INTEGER NOT NULL, UNIQUE(id), FOREIGN KEY (user_id) REFERENCES users(id), FOREIGN KEY (sight_id) REFERENCES sights(id))");

        buttonRu =  findViewById(R.id.imageButtonRu);
        buttonEn =  findViewById(R.id.imageButtonEn);
        temperature = findViewById(R.id.textView);
        about = findViewById(R.id.textView3);;
        icon = findViewById(R.id.imageView3);

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
        int draw_11 = R.drawable.park300;
        int draw_12 = R.drawable.primpark;
        int draw_13 = R.drawable.parkpob;
        int draw_14 = R.drawable.zinger;
        int draw_15 = R.drawable.dom_ioffa;
        int draw_16 = R.drawable.divo;
        int draw_17 = R.drawable.rusetnmus;
        int draw_18 = R.drawable.arktmus;
        int draw_19 = R.drawable.artilmus;
        int draw_20 = R.drawable.troic;
        int draw_21 = R.drawable.smolny;
        int draw_22 = R.drawable.ekat2monum;
        int draw_23 = R.drawable.alekscolu;

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
                "Архитектурно-планировочные решения и общее руководство осуществлял Юрий Фельтен.', 'Этьен Морис Фальконе', 59.9363632, 30.3019047, 'https://ru.wikipedia.org/wiki/%D0%9C%D0%B5%D0%B4%D0%BD%D1%8B%D0%B9_%D0%B2%D1%81%D0%B0%D0%B4%D0%BD%D0%B8%D0%BA', 3, '00:00', '24:00', 'Бесплатно', 'Бесплатно')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (5, " + draw_5 + ", 'Спас на крови' , 'Невский проспект.', 'наб. канала Грибоедова, 2Б', false, '1907 г.','Православный мемориальный однопрестольный храм во имя Воскресения Христова; сооружён в память того,\n" +
                "что на этом месте 1 марта 1881 года в результате покушения был смертельно ранен император Александр II" +
                "(выражение на крови указывает на кровь царя). Храм был сооружён как памятник царю на средства, собранные по всей России." +
                "Храм был возведён по указу императора Александра III в 1883—1907 годах по совместному проекту архитектора Альфреда Парланда и архимандрита Игнатия," +
                "который впоследствии от строительства отошёл. Проект выполнен в русском стиле, несколько напоминает московский собор Покрова на Рву." +
                "Строительство длилось 24 года. 6 августа 1907 года собор был освящён.', 'Альфред Александрович Парланд', 59.9397094, 30.3287002, 'http://spas.spb.ru/', 1, '10:30', '18:00', '450 руб', '100 руб')");

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
                "графики, скульптуры, нумизматики, декоративно-прикладного и народного искусства, а также архивные материалы.', 'Карло Росси', 59.9380167, 30.3320391, 'https://www.rusmuseum.ru/', 2, '10:00', '18:00' , '400 руб', '200 руб')");

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
                "вторую сцену на Крюковом канале (с 2013 года) и филиалы во Владивостоке (с 2016 года) и Владикавказе (с 2017).', 'Альберт Катеринович Кавос', 59.9258033,  30.2970917, 'https://www.mariinsky.ru/', 5, '10:00', '18:00', 'от 500 руб', 'от 500 руб')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (11, " + draw_11 + ", 'Парк 300-летия СПб' , 'Беговая.', 'Приморский пр., 74', false, '1995 г.', 'Парк 300-летия Санкт-Петербурга — расположен в северо-западной части Санкт-Петербурга на границе " +
                "Приневской низменности в северной части Невской губы. С севера парк ограничен Приморским проспектом и Приморским шоссе, а с востока — Яхтенной улицей. Общая площадь — 54 га.', 'Отсутствует', 59.9812775,  30.2008025, 'http://park300spb.ru/', 6, '07:00', '23:00', 'Бесплатно', 'Бесплатно')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (12, " + draw_12 + ", 'Приморский парк Победы' , 'Крестовский остров.', 'Крестовский пр., 23А', false, '1945 г.', 'Приморский Парк Победы был заложен 7 октября 1945 года в ознаменование победы в Великой Отечественной войне. " +
                "На месте сегодняшнего Приморского Парка Победы в допетровские времена располагались хуторские острова, отделенные друг от друга болотами и мелколесьем. Первые парковые работы начались в начале ХVIII века, " +
                "когда Петербург бурно строился и рос с каждым днем, а Крестовский остров принадлежал губернатору Александру Меншикову.', 'Отсутствует', 59.9719592,  30.243595, 'http://pppark.ru/', 6, '00:00', '24:00', 'Бесплатно', 'Бесплатно')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (13, " + draw_13 + ", 'Московский парк Победы' , 'Парк Победы.', 'Кузнецовская ул., 25', false, '1939 г.', 'Парк в Московском районе Санкт-Петербурга. Впервые заложен в 1939—1941 годах в соответствии с Генеральным планом развития Ленинграда как Парк культуры и отдыха. " +
                "Повторно заложен в 1945 году в честь Победы в Великой Отечественной войне как парк Победы.', 'Татьяна Борисовна Дубяго', 59.86896, 30.3248849, 'https://www.gupmpp.ru/', 6, '00:00', '24:00', 'Бесплатно', 'Бесплатно')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (14, " + draw_14 + ", 'Дом Зингера' , 'Невский проспект.', 'Невский пр., 28', false, '1904 г.', 'Шестиэтажное здание с мансардой в стиле модерн, площадью около 7000 м² было построено в 1902—1904 годах по проекту архитектора Павла Сюзора для «Акционерной компании Зингер в России». " +
                "Для строительства выбрали место с максимальной торгово-деловой активностью на пересечении Невского проспекта и Екатерининского канала. " +
                "Маститый архитектор с 30-летней карьерой за плечами взялся за проект для щедрого заказчика и решился на работу в стиле модерн. " +
                "До 1917 года здание принадлежало компании «Зингер»; в 1904—1911 годах значительная часть помещений арендовалась Санкт-Петербургским частным коммерческим банком. В годы Первой мировой войны на первом этаже здания находилось посольство США. С декабря 1919 года в здании располагался «Петрогосиздат» (с 1938 года — «Лениздат»), а в 1920-е — 1930-е годы — и другие издательства. " +
                "Также в здании велась книжная торговля, а с 1938 года в нём располагается «Дом книги».', 'Павел Юльевич Сюзор', 59.9354113, 30.3263798, 'https://dk-spb.ru/', 5, '09:00', '23:00', 'Бесплатно', 'Бесплатно')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (15, " + draw_15 + ", 'Доходный дом Ш.З. Иоффа' , 'Владимирская.', 'Загородный просп., 11', false, '1913 г.', 'Знаменитый ʺДом с башней на Пяти углахʺ находится в Санкт-Петербурге, на улице Рубинштейна, и является выдающимся архитектурным сооружением в стиле неоклассицизма. " +
                "Здание доходного дома было построено в 1913 году по проекту архитектора Александра Лишневского. Сначала трёхэтажный дом принадлежал купцам Лапиным, а позднее его приобрел богатый комиссионер Ш.З. Иофф. " +
                "Он имел аукционный зал на Загородном пр., 6, где торговал антиквариатом и мебелью.', 'Александр Львович Лишневский', 59.9262286, 30.3422761, 'https://wikipoints.ru/point/1147', 5, '00:00', '24:00', 'Бесплатно', 'Бесплатно')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (16, " + draw_16 + ", 'Диво-остров' , 'Крестовский остров.', 'Кемская ул., 1А', false, '2003 г.', 'Парк аттракционов «Диво Остров» был открыт в мае 2003 года и насчитывал 17 аттракционов. За первый месяц после открытия парк посетило 1 млн человек. " +
                "За время работы парк неоднократно получал различные награды: в 2004 году — «Хрустальное колесо» как лучший парк аттракционов в России, в 2006 году стал победителем национальной премии «Компания года» в номинации «Лидер отрасли». По данным на весну 2022 года парк насчитывает 42 аттракциона.', 'Отсутствует', 59.9726055, 30.2546344, 'https://www.divo-ostrov.ru/', 7, '12:00', '20:00', 'Бесплатно', 'Бесплатно')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (17, " + draw_17 + ", 'Этнографический музей' , 'Невский проспект.', 'Инженерная ул., 4/1', false, '1902 г.', 'Один из крупнейших этнографических музеев Европы. Основан, как этнографический отдел Русского музея, императором Николаем II во исполнение воли и в память его отца императора Александра " +
                "III Именным высочайшим указом № 11532 от 13 апреля 1895 года. «Об учреждении особого установления под названием „Русского Музея Императора Александра III“ и о представлении для сей цели приобретенного в казну Михайловского Дворца со всеми принадлежащими к нему флигелями, службами и садом».', 'Василий Фёдорович Свиньин', 59.9375708, 30.334019, 'https://ethnomuseum.ru/', 2, '10:00', '18:00', '350 руб', '100 руб')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (18, " + draw_18 + ", 'Музей Арктики и Антарктики' , 'Владимирская.', 'ул. Марата, 24А', false, '1930 г.', ' музей в Санкт-Петербурге, посвящённый историческим страницам научных исследований Арктики и Антарктики, " +
                "а также прилегающих к Арктике советских и российских территорий и Северного морского пути. Созданию музея предшествовало широкомасштабное научное исследование Арктики советскими учёными, развернувшееся после основания в 1920 году Северной научно-промысловой экспедиции (Севэкспедиции), которая в 1925 году была преобразована в Институт по изучению Севера — ныне Арктический и антарктический научно-исследовательский институт. В настоящий момент музей Арктики и Антарктики является крупнейшим в мире музеем, посвящённым полярной тематике. " +
                "Его фонды насчитывают около 70 тысяч единиц хранения, среди которых есть и подлинные артефакты, и фотографии (около 34 тысяч), и произведения искусства. Наиболее примечательными коллекциями считаются археологическая коллекция предметов быта поморов первой половины XVII века, найденных на Таймыре, коллекция декоративно-прикладного искусства народов Севера, коллекция предметов изобразительного искусства студентов Института народов Севера 1930-х годов и комплекс предметов оборудования и снаряжений дрейфующей станции «Северный полюс-1». ', 'Мельников Авраам Иванович', 59.9274947, 30.3528474, 'https://www.polarmuseum.ru/', 2, '10:00', '17:30', '350 руб', '100 руб')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (19, " + draw_19 + ", 'Музей артиллерии' , 'Горьковская.', 'парк Александровский, 7', false, '1703 г.', 'Экспозиция Военно-исторического музея артиллерии, инженерных войск и войск связи размещена в 13 залах, размещённых в хронологическом порядке. На сегодняшний день коллекция музея насчитывает свыше 850 тысяч экспонатов и охватывает время c XIV века до наших дней. После реконструкции в ноябре 2002 года во внутреннем дворе музея представлено свыше 250 образцов пушек, " +
                "самоходных артиллерийских установок (САУ), ракетных комплексов и несколько танков. Среди прочего, в музее хранятся отличающиеся богатой узорчатой отделкой старинные бронзовые орудия русских и европейских литейщиков — шведские и французские трофеи. Есть несколько пушек работы выдающегося мастера Андрея Чохова. В залах представлено множество советских и немецких образцов стрелкового и артиллерийского вооружения времён Великой Отечественной войны.', 'Таманский Иван Трофимович', 59.9527868, 30.3147005, 'https://artillery-museum.ru/', 2, '11:00', '18:00', '500 руб', '200 руб')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (20, " + draw_20 + ", 'Троицкий собор' , 'Технологический институнт.', 'Измайловский пр., 7А', false, '1835 г.', 'Православный собор на Троицком проспекте в Адмиралтейском районе Санкт-Петербурга. Полное историческое наименование — Собор Святой Живоначальной Троицы Лейб-Гвардии Измайловского полка. Приход храма относится к Санкт-Петербургской епархии Русской православной церкви, входит в состав Адмиралтейского благочиннического округа. Настоятель — митрофорный протоиерей Геннадий Бартов.\n" +
                "', 'Василий Петрович Стасов', 59.9165907, 30.3077249, 'http://www.izmsobor.ru/ru/', 1, '09:00', '19:00', 'Бесплатно', 'Бесплатно')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (21, " + draw_21 + ", 'Смольный собор' , 'Чернышевская.', 'пл. Растрелли, 1', false, '1748 г.', 'Православный храм Санкт-Петербурга. Входит в состав архитектурного ансамбля Смольного монастыря, который находится в Санкт-Петербурге на левом берегу Невы на Смольной набережной. Воскресенский Смольный собор исторически является храмом учебных заведений Санкт-Петербурга, храмом учащихся, поэтому приоритетным направлением деятельности клира и мирян храма является дело духовно-нравственного просвещения юношества. " +
                "В период с 1990 до 2015 года являлся концертной площадкой классической музыки.\n" +
                "Начиная с 2020 года, «Фонд содействия восстановлению объектов истории и культуры» при поддержке «Газпром» планирует начать работы по достройке колокольни собора, строительство которой было включено в проект Бартоломео Растрелли, но не реализовано. При удачном стечении обстоятельств, Смольный собор может стать самой высокой постройкой в центре города.', 'Франческо Растрелли', 59.9489144, 30.3919439, 'http://smolnyspb.ru/', 1, '08:00', '20:00', '200 руб', '100 руб')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (22, " + draw_22 + ", 'Памятник Екатерине II' , 'Невский проспект.', 'пл. Островского, 1', false, '1873 г.', 'В начале 1861 году Академия наук назначила конкурс на проект памятника Екатерины II, приуроченный к 100-летию её восшествия на престол. Памятник предполагалось установить у Екатерининского дворца в Царском Селе. Было представлено около 8 проектов, премии удостоился проект художника М. О. Микешина, который и был утвержден.\n" +
                "В 1865 г. было решено установить его в Петербурге перед Александринским театром. Тогда же было решено прибавить на памятнике фигуры Бецкого и Безбородко[1].\n" +
                "Модель памятника, выполненный в масштабе 1⁄16 натуральной величины, была приобретена для царскосельского дворца и находится в павильоне «Грот» в Царском Селе. Закладка памятника в центре сквера на Александрийской площади состоялась 24 ноября 1869 года в присутствии Александра II и членов императорской фамилии. Памятник был открыт в 1873 году.', 'Давид Иванович Гримм', 59.9334345, 30.3372619, 'https://ru.wikipedia.org/wiki/%D0%9F%D0%B0%D0%BC%D1%8F%D1%82%D0%BD%D0%B8%D0%BA_%D0%95%D0%BA%D0%B0%D1%82%D0%B5%D1%80%D0%B8%D0%BD%D0%B5_II_(%D0%A1%D0%B0%D0%BD%D0%BA%D1%82-%D0%9F%D0%B5%D1%82%D0%B5%D1%80%D0%B1%D1%83%D1%80%D0%B3)', 3, '07:00', '22:00', 'Бесплатно', 'Бесплатно')");

        db.execSQL("INSERT OR IGNORE INTO sights (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (23, " + draw_23 + ", 'Александровская колонна' , 'Адмиралтейская.', 'Дворцовая пл', false, '1834 г.', 'Памятник в стиле ампир, находящийся в центре Дворцовой площади Санкт-Петербурга. Воздвигнута в 1834 году архитектором Огюстом Монферраном по указу императора Николая I в память о победе его старшего брата Александра I над Наполеоном. Находится в ведении Государственного Эрмитажа.', 'Огюст Монферран', 59.9386905, 30.3153571, 'https://ru.wikipedia.org/wiki/%D0%90%D0%BB%D0%B5%D0%BA%D1%81%D0%B0%D0%BD%D0%B4%D1%80%D0%BE%D0%B2%D1%81%D0%BA%D0%B0%D1%8F_%D0%BA%D0%BE%D0%BB%D0%BE%D0%BD%D0%BD%D0%B0', 3, '00:00', '24:00', 'Бесплатно', 'Бесплатно')");

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
                "The construction lasted 24 years. On August 6, 1907, the cathedral was consecrated.', 'Alfred Alexandrovich Parland', 59.9397094, 30.3287002, 'http://spas.spb.ru/', 1, '10:30', '18:00', '450 rub', '100 rub')");

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
                "the second stage on the Kryukov Canal (since 2013) and branches in Vladivostok (since 2016) and Vladikavkaz (since 2017).', 'Albert Katerinovich Kavos', 59.9258033,  30.2970917, 'https://www.mariinsky.ru/', 5, '10:00', '18:00', 'from 500 rub', 'from 500 rub')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (11, " + draw_11 + ", 'Park 300th years of SPb' , 'Begovaya.', 'Primorsky pr., 74', false, '1995 y.', 'Park of the 300th Anniversary of St. Petersburg - located in the northwestern part of St. Petersburg on the border" +
                "Neva lowland in the northern part of the Neva Bay. From the north, the park is bounded by Primorsky Prospekt and Primorskoye Highway, and from the east by Yakhtennaya Street. The total area is 54 hectares.', 'Missing', 59.9812775,  30.2008025, 'http://park300spb.ru/', 6, '07:00', '23:00', 'Free', 'Free')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (12, " + draw_12 + ", 'Seaside Park Victory' , 'Krestovsky island.', 'Krestovsky pr., 23A', false, '1945 y.', 'Seaside Victory Park was founded on October 7, 1945 to commemorate the victory in the Great Patriotic War. On the site of today s Primorsky Victory Park in pre-Petrine times, there were farm islands, " +
                "separated from each other by swamps and low forests. The first park work began at the beginning of the 18th century, when St. Petersburg was rapidly built and grew every day, and Krestovsky Island belonged to the governor Alexander Menshikov.', 'Missing', 59.9719592,  30.243595, 'http://pppark.ru/', 6, '00:00', '24:00', 'Free', 'Free')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (13, " + draw_13 + ", 'Moscow Victory Park' , 'Park Pobedi.', 'Kuznetsovskaya st., 25', false, '1939 y.', 'Park in the Moskovsky district of St. Petersburg. It was first founded in 1939-1941 in accordance with the General Plan for the Development of Leningrad as a Park of Culture and Leisure.\n" +
                "It was laid down again in 1945 in honor of the Victory in the Great Patriotic War as Victory Park.', 'Tatyana Borisovna Dubyago', 59.86896, 30.3248849, 'https://www.gupmpp.ru/', 6, '00:00', '24:00', 'Free', 'Free')");

        db.execSQL("INSERT OR IGNORE INTO sights_en  (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (14, " + draw_14 + ", 'Zinger House' , 'Nevsky Prospect.', 'Nevsky prospect., 28', false, '1904 y.', 'The six-storey building with an attic in Art Nouveau style, with an area of \u200B\u200Babout 7,000 m², was built in 1902-1904 according to the design of the architect Pavel Syuzor for the Singer Joint-Stock Company in Russia.\n" +
                "For the construction, they chose a place with maximum trade and business activity at the intersection of Nevsky Prospekt and the Ekaterininsky Canal.\n" +
                "A venerable architect with a 30-year career behind him took on a project for a generous client and decided to work in the Art Nouveau style.\n" +
                "Until 1917, the building belonged to the Singer company; in 1904-1911, a significant part of the premises was rented by the St. Petersburg private commercial bank. During the First World War, the US embassy was located on the first floor of the building. Since December 1919, the building housed Petrogosizdat (since 1938 - Lenizdat), and in the 1920s - 1930s - other publishing houses.\n" +
                "Also, the book trade was carried out in the building, and since 1938 it has housed the Book House.', 'Pavel Yulievich Syuzor', 59.9354113, 30.3263798, 'https://dk-spb.ru/', 5, '09:00', '23:00', 'Free', 'Free')");

        db.execSQL("INSERT OR IGNORE INTO sights_en  (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (15, " + draw_15 + ", 'Profitable house Sh.Z. Ioffa' , 'Vladimirskaya.', 'Zagorodny Ave., 11', false, '1913 y.', 'The famous \"House with a Tower at the Five Corners\" is located in St. Petersburg, on Rubinshteina Street, and is an outstanding architectural structure in the neoclassical style.\n" +
                "The building of the apartment building was built in 1913 according to the project of the architect Alexander Lishnevsky. At first, the three-story house belonged to the merchants Lapins, and later it was acquired by a wealthy commission agent Sh.Z. Ioff.\n" +
                "He had an auction room at 6, Zagorodny Pr., where he sold antiques and furniture.', 'Alexander Lvovich Lishnevsky', 59.9262286, 30.3422761, 'https://wikipoints.ru/point/1147', 5, '00:00', '24:00', 'Free', 'Free')");

        db.execSQL("INSERT OR IGNORE INTO sights_en  (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (16, " + draw_16 + ", 'Divo Ostrov' , 'Krestovsky island.', 'Kemskaya st., 1A', false, '2003 y.', 'Amusement park \"Divo Ostrov\" was opened in May 2003 and consisted of 17 attractions. In the first month after the opening, the park was visited by 1 million people.\n" +
                "During its operation, the park has repeatedly received various awards: in 2004 - \"Crystal Wheel\" as the best amusement park in Russia, in 2006 became the winner of the national award \"Company of the Year\" in the nomination \"Industry Leader\". As of spring 2022, the park has 42 rides.', 'Missing', 59.9726055, 30.2546344, 'https://www.divo-ostrov.ru/', 7, '12:00', '20:00', 'Free', 'Free')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (17, " + draw_17 + ", 'Ethnographical museum' , 'Nevsky Prospect.', 'Engineering street, 4/1', false, '1902 y.', 'One of the largest ethnographic museums in Europe. It was founded as the ethnographic department of the Russian Museum by Emperor Nicholas II in fulfillment of the will and in memory of his father Emperor Alexander III by the Nominal Imperial Decree No. " +
                "11532 of April 13, 1895. “On the establishment of a special institution called the “Russian Museum of Emperor Alexander III” and on the presentation for this purpose of the Mikhailovsky Palace acquired in the treasury with all outbuildings, services and a garden belonging to it', 'Vasily Fedorovich Svinin', 59.9375708, 30.334019, 'https://ethnomuseum.ru/', 2, '10:00', '18:00', '350 rub', '100 rub')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (18, " + draw_18 + ", 'Museum of the Arctic and Antarctic' , 'Vladimirskaya.', 'st. Marata, 24A', false, '1930 y.', 'Museum in St. Petersburg, dedicated to the historical pages of scientific research in the Arctic and Antarctic,\n" +
                "as well as the Soviet and Russian territories adjacent to the Arctic and the Northern Sea Route. The creation of the museum was preceded by a large-scale scientific study of the Arctic by Soviet scientists, which unfolded after the founding in 1920 of the Northern Scientific and Fishing Expedition (Sevexpedition), which in 1925 was transformed into the Institute for the Study of the North - now the Arctic and Antarctic Research Institute. Currently, the Museum of the Arctic and Antarctic is the largest museum in the world dedicated to the polar theme. Its funds include about 70 thousand items, among which there are genuine artifacts, " +
                "photographs (about 34 thousand), and works of art. The most notable collections are the archaeological collection of household items of the Pomors of the first half of the 17th century found in Taimyr, the collection of arts and crafts of the peoples of the North, the collection of fine art objects of students of the Institute of the Peoples of the North of the 1930s and the complex of equipment and supplies of the drifting station \"North Pole - one\".', 'Melnikov Avraam Ivanovich', 59.9274947, 30.3528474, 'https://www.polarmuseum.ru/', 2, '10:00', '17:30', '350 rub', '100 rub')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (19, " + draw_19 + ", 'Artillery Museum' , 'Gorkovskaya.', 'Aleksandrovsky park, 7', false, '1703 y.', 'The exposition of the Military Historical Museum of Artillery, Engineer and Signal Corps is located in 13 halls, placed in chronological order. To date, the museum`s collection includes over 850 thousand exhibits and covers the time from the XIV century to the present day. After reconstruction in November 2002, over 250 examples of cannons, self-propelled artillery mounts (SAUs), " +
                "missile systems and several tanks are displayed in the courtyard of the museum. Among other things, the museum contains ancient bronze tools of Russian and European casters, distinguished by their rich patterned finish - Swedish and French trophies. There are several cannons made by the outstanding craftsman Andrey Chokhov. The halls display many Soviet and German models of small arms and artillery weapons from the Great Patriotic War.', 'Tamansky Ivan Trofimovich', 59.9527868, 30.3147005, 'https://artillery-museum.ru/', 2, '11:00', '18:00', '500 rub', '200 rub')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (20, " + draw_20 + ", 'Trinity Cathedral' , 'Teknologicheskiy institut.', 'Izmailovsky pr., 7A', false, '1835 y.', 'Orthodox Cathedral on Troitsky Prospekt in the Admiralteysky district of St. Petersburg. The full historical name is the Cathedral of the Holy Life-Giving Trinity of the Life Guards of the Izmailovsky Regiment. The parish of the temple belongs to the St. Petersburg diocese of the Russian Orthodox Church, is part of the Admiralty Deanery District. Rector - Mitred Archpriest Gennady Bartov.', 'Vasily Petrovich Stasov', 59.9165907, 30.3077249, 'http://www.izmsobor.ru/ru/', 1, '09:00', '19:00', 'Free', 'Free')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (21, " + draw_21 + ", 'Smolny Cathedral' , 'Chernyshevskaya.', 'sq. Rastrelli, 1', false, '1748 y.', 'Orthodox church in St. Petersburg. It is part of the architectural ensemble of the Smolny Monastery, which is located in St. Petersburg on the left bank of the Neva on Smolnaya Embankment. The Resurrection Smolny Cathedral has historically been a temple of educational institutions in St. Petersburg, a temple of students, therefore, the spiritual and moral education of youth is a priority for the clergy and laity of the temple. In the period from 1990 to 2015, it was a concert venue for classical music. Starting from 2020, the Foundation for Assistance to the " +
                "Restoration of Historical and Cultural Objects, with the support of Gazprom, plans to begin work on completing the construction of the bell tower of the cathedral, the construction of which was included in the project of Bartolomeo Rastrelli, " +
                "but was not implemented. With a good combination of circumstances, the Smolny Cathedral can become the tallest building in the city center.', 'Francesco Rastrelli', 59.9489144, 30.3919439, 'http://smolnyspb.ru/', 1, '08:00', '20:00', '200 rub', '100 rub')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (22, " + draw_22 + ", 'Monument to Catherine II' , 'Nevsky Avenue.', 'sq. Ostrovsky, 1', false, '1873 y.', 'In early 1861, the Academy of Sciences announced a competition for the design of a monument to Catherine II, timed to coincide with the 100th anniversary of her accession to the throne. The monument was supposed to be erected at the Catherine Palace in Tsarskoye Selo. About 8 projects were presented, the project of the artist M. O. Mikeshin was awarded the prize, which was approved.\n" +
                    "In 1865, it was decided to install it in St. Petersburg in front of the Alexandrinsky Theatre. Then it was decided to add the figures of Betsky and Bezborodko to the monument.\n" +
                    "A model of the monument, made on a scale of 1⁄16 natural size, was purchased for the Tsarskoye Selo Palace and is located in the Grotto pavilion in Tsarskoye Selo. The laying of the monument in the center of the square on Alexandria Square took place on November 24, 1869 in the presence of Alexander II and members of the imperial family. The monument was opened in 1873.', 'David Ivanovich Grimm', 59.9334345, 30.3372619, 'https://ru.wikipedia.org/wiki/%D0%9F%D0%B0%D0%BC%D1%8F%D1%82%D0%BD%D0%B8%D0%BA_%D0%95%D0%BA%D0%B0%D1%82%D0%B5%D1%80%D0%B8%D0%BD%D0%B5_II_(%D0%A1%D0%B0%D0%BD%D0%BA%D1%82-%D0%9F%D0%B5%D1%82%D0%B5%D1%80%D0%B1%D1%83%D1%80%D0%B3)', 3, '07:00', '22:00', 'Free', 'Free')");

        db.execSQL("INSERT OR IGNORE INTO sights_en (id, image, sightName, metro, location, stared, dateOfBuild, discription, architect, latitude, longitude, website, type,  openTime, closeTime, price, priceForKids) VALUES (23, " + draw_23 + ", 'Alexander Column' , 'Admiralteyskaya.', 'Palace Square.', false, '1834 y.', 'A monument in the Empire style, located in the center of Palace Square in St. Petersburg. Erected in 1834 by the architect Auguste Montferrand by decree of Emperor Nicholas I in memory of the victory of his elder brother Alexander I over Napoleon. It is under the jurisdiction of the State Hermitage.', 'Auguste Montferrand', 59.9386905, 30.3153571, 'https://ru.wikipedia.org/wiki/%D0%90%D0%BB%D0%B5%D0%BA%D1%81%D0%B0%D0%BD%D0%B4%D1%80%D0%BE%D0%B2%D1%81%D0%BA%D0%B0%D1%8F_%D0%BA%D0%BE%D0%BB%D0%BE%D0%BD%D0%BD%D0%B0', 3, '00:00', '24:00', 'Free', 'Free')");

        db.execSQL("INSERT OR IGNORE INTO types VALUES (1, 'cathedral')");
        db.execSQL("INSERT OR IGNORE INTO types VALUES (2, 'museum')");
        db.execSQL("INSERT OR IGNORE INTO types VALUES (3, 'sight')");
        db.execSQL("INSERT OR IGNORE INTO types VALUES (4, 'other')");
        db.execSQL("INSERT OR IGNORE INTO types VALUES (5, 'architecture')");
        db.execSQL("INSERT OR IGNORE INTO types VALUES (6, 'parks')");
        db.execSQL("INSERT OR IGNORE INTO types VALUES (7, 'entertainment')");

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