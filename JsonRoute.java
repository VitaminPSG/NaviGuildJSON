public class JsonRoute {
    //Карта расписания, ключ - рабочий период, объект - массив [время отправления с первой остановки][ключ к интервалу движения]
    public HashMap<Integer, Integer[][]> period;
    //Карта расписания, ключ - ключ интервала движения, объект - строка вида "1,1,1,2,..." (интервал движения от 1 остановки до 2, от 2 до 3, и.т.д.)
    public HashMap<Integer, String> delays;
    //Ключ маршрута
    private int id;
    //Название ("Автозаводская ас - Дражня")
    private String name;
    //Номер маршрута
    private String number;
    //Тип транспорта
    private int trans;
    //Тип движения(0 - А->Б, 7 - Б->А)
    private int type;
    //Массив ключей остановок
    private int[] stops;

    public HashMap<Integer, Integer[][]> getPeriod() {
        return period;
    }

    public void setPeriod(HashMap<Integer, Integer[][]> period) {
        this.period = period;
    }

    public HashMap<Integer, String> getDelays() {
        return delays;
    }

    public void setDelays(HashMap<Integer, String> delays) {
        this.delays = delays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getTrans() {
        return trans;
    }

    public void setTrans(int trans) {
        this.trans = trans;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int[] getStops() {
        return stops;
    }

    public void setStops(int[] stops) {
        this.stops = stops;
    }
}
