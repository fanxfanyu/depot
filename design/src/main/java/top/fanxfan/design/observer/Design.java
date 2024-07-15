package top.fanxfan.design.observer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {
    public static void main(String[] args) {
        var weather = new Weather();
        Orcs orcs = new Orcs();
        weather.addObserver(orcs);
        weather.addObserver(new Hobbits());

        weather.timePasses();
        weather.timePasses();
        weather.timePasses();
        weather.timePasses();

        weather.removeObserver(orcs);

        log.info("--remove orcs after");

        weather.timePasses();
        weather.timePasses();
        weather.timePasses();
        weather.timePasses();

        // Generic observer inspired by Java Generics and Collections by Naftalin & Wadler
        log.info("--Running generic version--");
        var genericWeather = new GWeather();
        genericWeather.addObserver(new GOrcs());
        genericWeather.addObserver(new GHobbits());

        genericWeather.timePasses();
        genericWeather.timePasses();
        genericWeather.timePasses();
        genericWeather.timePasses();

        // 已知使用
        ApplicationEventPublisher publisher = new ApplicationEventPublisher() {
            @Override
            public void publishEvent(Object event) {
                ApplicationEventPublisher.super.publishEvent(new MagEvent());
            }
        };
    }

    static class MagEvent extends ApplicationEvent {
        public MagEvent() {
            super("");
            log.info("create event");
        }

        public MagEvent(Object source) {
            super(source);
            log.info("create event");

        }
    }

    @Getter
    @AllArgsConstructor
    enum WeatherType {
        COLD("COLD"),
        RAINY("RAINY"),
        SUNNY("SUNNY"),
        WINDY("WINDY");

        private final String description;
    }

    interface WeatherObserver {
        void updater(WeatherType type);
    }

    static class Orcs implements WeatherObserver {

        @Override
        public void updater(WeatherType type) {
            log.info("the orcs are facing {} weather now", type.getDescription());
        }
    }

    static class Hobbits implements WeatherObserver {

        @Override
        public void updater(WeatherType type) {
            log.info("the hobbits are facing {} weather now", type.getDescription());
        }
    }

    @Getter
    @Setter
    static class Weather {
        private WeatherType currentWeather;
        private List<WeatherObserver> observers;

        public Weather() {
            observers = new ArrayList<>();
            currentWeather = WeatherType.SUNNY;
        }

        public void addObserver(WeatherObserver observers) {
            this.observers.add(observers);
        }

        private void notifyObservers() {
            for (WeatherObserver observer : observers) {
                observer.updater(currentWeather);
            }
        }

        public void removeObserver(WeatherObserver observer) {
            observers.remove(observer);
        }

        public void timePasses() {
            var values = WeatherType.values();
            currentWeather = values[(currentWeather.ordinal() + 1) % values.length];
            log.info("the weather change {}", currentWeather);
            notifyObservers();
        }
    }


    @Getter
    @Setter
    @AllArgsConstructor
    static class GWeather extends Observable<GWeather, Race, WeatherType> {
        private WeatherType currentWeather;

        public GWeather() {
            this.currentWeather = WeatherType.SUNNY;
        }

        public void timePasses() {
            var values = WeatherType.values();
            currentWeather = values[(currentWeather.ordinal() + 1) % values.length];
            log.info("the weather change to {}", currentWeather);
            notifyObservers(currentWeather);
        }
    }

    abstract static class Observable<S extends Observable<S, O, A>, O extends Observer<S, O, A>, A> {
        protected final List<O> observers;

        public Observable() {
            this.observers = new CopyOnWriteArrayList<>();
        }

        public void addObserver(O observer) {
            observers.add(observer);
        }

        public void removeObserver(O observer) {
            observers.remove(observer);
        }

        @SuppressWarnings("unchecked")
        public void notifyObservers(A argument) {
            for (O observer : observers) {
                observer.update((S) this, argument);
            }

        }
    }

    interface Observer<S extends Observable<S, O, A>, O extends Observer<S, O, A>, A> {
        void update(S subject, A argument);
    }

    interface Race extends Observer<GWeather, Race, WeatherType> {

    }

    static class GOrcs implements Race {

        @Override
        public void update(GWeather subject, WeatherType argument) {
            log.info("the orcs are facing {} weather noe", argument.getDescription());
        }
    }

    static class GHobbits implements Race {

        @Override
        public void update(GWeather subject, WeatherType argument) {
            log.info("the hobbits are facing {} weather noe", argument.getDescription());
        }
    }
}
