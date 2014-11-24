public class WriteRealmTask extends AsyncTask<JsonData, Integer, Void> {
    Context context;
    WriteRealmListener writeRealmListener;
    String mapURI;

    public WriteRealmTask(Context context, String mapURI, JsonData jsonData, WriteRealmListener writeRealmListener) {
        this.context = context;
        this.writeRealmListener = writeRealmListener;
        this.mapURI = mapURI;
        execute(jsonData);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (writeRealmListener != null)
            writeRealmListener.onProgressChange(values[0]);
    }

    @Override
    protected Void doInBackground(JsonData... params) {
        if (params.length < 1) return null;
        JsonData jsonData = params[0];
        if (jsonData == null) return null;
        Realm baseRealm = Realm.getInstance(context, false);
        RealmResults<RealmCity> cities = baseRealm.where(RealmCity.class).equalTo("select", true).findAll();
        baseRealm.beginTransaction();
        String dbName = jsonData.getNameDb();
        if (dbName == null || dbName.isEmpty())
            dbName = String.valueOf(jsonData.hashCode());
        baseRealm.where(RealmCity.class).equalTo("realmFileName", dbName).findAll().clear();
        RealmCity city = baseRealm.createObject(RealmCity.class);
        city.setName(jsonData.getCity());
        city.setRealmFileName(dbName);
        city.setMapURI(this.mapURI);
        city.setSelect(cities.size() == 0);
        baseRealm.commitTransaction();
        Realm.deleteRealmFile(context, city.getRealmFileName());
        Realm realm = Realm.getInstance(context, city.getRealmFileName(), false);
        realm.beginTransaction();
        int stopSize = jsonData.getStops().size();
        int routeSize = jsonData.getRoutes().size();
        int buildingSize = jsonData.getBuildings().size();
        int progressSize = stopSize + routeSize + buildingSize;
        Log.d("TAG", "Write Stop to DB");
        for (int i = 0; i < stopSize; i++) {
            JsonStop s = jsonData.getStops().get(i);
            if (i % 10 == 0) {
                publishProgress((int) (100 * i / progressSize));
            }
            RealmStop stop = realm.createObject(RealmStop.class);
            int[] routesId = s.getRoutes();
            for (int id : routesId) {
                RealmString realmString = realm.createObject(RealmString.class);
                realmString.setId(String.valueOf(id));
                stop.getRoutesId().add(realmString);
            }
            stop.setFavorite(false);
            stop.setLatitude(s.getPoint()[0]);
            stop.setLongitude(s.getPoint()[1]);
            stop.setName(s.getName());
            stop.setId(String.valueOf(s.getId()));
            stop.setTransport(s.getTransport());

        }
        Log.d("TAG", "Write Route to DB");
        for (int j = 0; j < routeSize; j++) {
            JsonRoute r = jsonData.getRoutes().get(j);
            if (j % 10 == 0) {
                publishProgress((int) (100 * (j + stopSize) / progressSize));
            }
            int[] stopsId = r.getStops();
            RealmRoute route = realm.createObject(RealmRoute.class);
            for (int id : stopsId) {
                RealmString realmString = realm.createObject(RealmString.class);
                realmString.setId(String.valueOf(id));
                route.getStopsId().add(realmString);
            }
            HashMap<Integer, Integer[][]> periods = r.getPeriod();
            for (Integer key : periods.keySet()) {
                RealmPeriod period = realm.createObject(RealmPeriod.class);
                Integer[][] times = periods.get(key);
                for (int i = 0; i < times[0].length; i++) {
                    RealmTime realmTime = realm.createObject(RealmTime.class);
                    realmTime.setTime(times[0][i]);
                    realmTime.setDelayId(times[1][i]);
                    period.getTimes().add(realmTime);
                }
                period.setWorkDay(workDayToByte(key));
                route.getPeriods().add(period);
            }
            HashMap<Integer, String> delays = r.getDelays();
            for (Integer key : delays.keySet()) {
                String value = delays.get(key);
                RealmDelay delay = realm.createObject(RealmDelay.class);
                delay.setId(key);
                delay.setValue(value);
                route.getDelays().add(delay);
            }
            route.setName(r.getName());
            route.setFavorite(false);
            route.setId(String.valueOf(r.getId()));
            route.setNumber(r.getNumber());
            route.setTransport(r.getTrans());
            route.setType(r.getType());
        }

        Log.d("TAG", "Write Building to DB");
        int i = 0;
        int buildingIndex = 0;
        for (String key : jsonData.getBuildings().keySet()) {
            if (i % 10 == 0) {
                publishProgress((int) (100 * (i + stopSize + routeSize) / progressSize));
            }
            ArrayList<JsonBuilding> buildings = jsonData.getBuildings().get(key);
            RealmStreet street = realm.createObject(RealmStreet.class);
            street.setFavorite(false);
            street.setName(key);
            street.setId(String.valueOf(key.hashCode()));
            for (JsonBuilding b : buildings) {
                RealmBuilding building = realm.createObject(RealmBuilding.class);
                building.setName(new StringBuilder(key).append(", ").append(b.getNum()).toString());
                building.setFavorite(false);
                building.setLatitude(b.getPoint()[0]);
                building.setLongitude(b.getPoint()[1]);
                building.setNumber(b.getNum());
                building.setId(String.valueOf(buildingIndex));
                street.getBuildings().add(building);
                buildingIndex++;
            }
            i++;
        }
        Log.d("TAG", "Complete");
        realm.commitTransaction();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        writeRealmListener.onWriteCompiled();
        writeRealmListener.onProgressChange(100);
    }

    public interface WriteRealmListener {
        void onWriteCompiled();

        void onProgressChange(int progress);
    }
}
