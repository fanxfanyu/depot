package top.fanxfan.design.chain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.*;

/**
 * chain of responsibility
 *
 * @author fanxfan
 */
@Slf4j
public class Design {

    public static void main(String[] args) {
        OrcKing orcKing = new OrcKing();
        orcKing.makeRequest(new Request(RequestType.DEFEND_CASTLE, "defend castle"));
        orcKing.makeRequest(new Request(RequestType.TORTURE_PRISONER, "torture prisoner"));
        orcKing.makeRequest(new Request(RequestType.COLLECT_TAX, "collect tax"));

        // 已知使用
        // java.util.logging.Logger#log()
        // javax.servlet.Filter#doFilter()
        Logger anonymousLogger = Logger.getLogger("Design");
        log.error("Logger class {}", anonymousLogger.getClass().getName());
        anonymousLogger.setFilter(new Filter() {
            @Override
            public boolean isLoggable(LogRecord record) {
                return record.getLevel() == Level.INFO;
            }
        });
        anonymousLogger.addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                log.error("Handler publish");
            }

            @Override
            public void flush() {
                log.error("Handler flush");
            }

            @Override
            public void close() throws SecurityException {
                log.error("Handler close");
            }
        });
        anonymousLogger.addHandler(new Handler() {
            @Override
            public void publish(LogRecord record) {
                log.error("Handler1 publish");
            }

            @Override
            public void flush() {
                log.error("Handler1 flush");
            }

            @Override
            public void close() throws SecurityException {
                log.error("Handler1 close");
            }
        });
        LogRecord logRecord = new LogRecord(Level.INFO, "test");
        logRecord.setLongThreadID(Thread.currentThread().getId());
        anonymousLogger.log(logRecord);

    }

    enum RequestType {
        COLLECT_TAX,
        DEFEND_CASTLE,
        TORTURE_PRISONER;
    }

    @Getter
    @Setter
    static class Request {
        private boolean handle;
        private String requestDescription;
        private RequestType requestType;

        public Request(RequestType requestType, String requestDescription) {
            this.requestType = requestType;
            this.requestDescription = requestDescription;
        }

        public boolean isHandle() {
            return false;
        }

        public void markHandled() {
            this.handle = true;
        }

        @Override
        public String toString() {
            return getRequestDescription();
        }
    }

    interface RequestHandler {

        boolean canHandlerRequest(Request request);

        int getPriority();

        void handle(Request request);

        String name();
    }


    static class OrcSoldier implements RequestHandler {
        public OrcSoldier() {
        }

        @Override
        public boolean canHandlerRequest(Request request) {
            return request.getRequestType() == RequestType.COLLECT_TAX;
        }

        @Override
        public int getPriority() {
            return 1;
        }

        @Override
        public void handle(Request request) {
            request.markHandled();

        }

        @Override
        public String name() {
            return "Orc Soldier";
        }
    }

    static class OrcOfficer implements RequestHandler {

        public OrcOfficer() {
        }

        @Override
        public boolean canHandlerRequest(Request request) {
            return request.getRequestType() == RequestType.TORTURE_PRISONER;
        }

        @Override
        public int getPriority() {
            return 3;
        }

        @Override
        public void handle(Request request) {
            request.markHandled();
            log.info("{} handling request {}", name(), request);
        }

        @Override
        public String name() {
            return "orc officer";
        }
    }


    static class OrcKing {
        private List<RequestHandler> handlers;

        public OrcKing() {
            buildChain();
        }

        private void buildChain() {
            handlers = Arrays.asList(new OrcCommander(), new OrcOfficer(), new OrcSoldier());
        }

        public void makeRequest(Request request) {
            handlers
                    .stream()
                    .sorted(Comparator.comparing(RequestHandler::getPriority))
                    .filter(handler -> handler.canHandlerRequest(request))
                    .findFirst()
                    .ifPresent(handler -> handler.handle(request));
        }
    }

    static class OrcCommander implements RequestHandler {
        public OrcCommander() {

        }


        @Override
        public boolean canHandlerRequest(Request request) {
            return request.getRequestType() == RequestType.DEFEND_CASTLE;
        }

        @Override
        public int getPriority() {
            return 2;
        }

        @Override
        public void handle(Request request) {
            request.markHandled();
            log.info("{} handling request {}", name(), request);
        }

        @Override
        public String name() {
            return "orc commander";
        }
    }


}
