package top.fanxfan.design.abstract_factory;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import top.fanxfan.design.exception.CustomException;

/**
 * abstract factory
 * 用于创建相关对象家族的接口，而无需指定其具体类。
 *
 * @author fanxfan
 */
@Slf4j
public class Design {
    @SneakyThrows
    public static void main(String[] args) {
        // 创建同一组的接口，无需指定具体实现类
        var factory = FactoryProducer.getFactory(Type.ELF);
        log.info("getDescription {}", factory.createKing().getDescription());
        factory = FactoryProducer.getFactory(Type.ORC);
        log.info("getDescription {}", factory.createKing().getDescription());

        // 已知使用
        // javax.xml.parsers.DocumentBuilderFactory
        // javax.xml.transform.TransformerFactory
        // javax.xml.xpath.XPathFactory

        // DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance("", null);
        // log.info("documentBuilderFactory {}", documentBuilderFactory.getClass().getName());
        // TransformerFactory transformerFactory = TransformerFactory.newInstance("", null);
        // log.info("transformerFactory {}", transformerFactory.getClass().getName());
        // XPathFactory xPathFactory = XPathFactory.newInstance("");
        // log.info("xPathFactory {}", xPathFactory.getClass().getName());
    }


    interface King {
        String getDescription();
    }

    static class ElfKing implements King {
        private static final String DESCRIPTION = "efl king";

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }
    }

    static class OrcKing implements King {
        private static final String DESCRIPTION = "orc king";

        @Override
        public String getDescription() {
            return DESCRIPTION;
        }
    }

    interface KingdomFactory {
        King createKing();
    }

    static class ElfKingdomFactory implements KingdomFactory {
        @Override
        public King createKing() {
            return new ElfKing();
        }
    }

    static class OrcKingdomFactory implements KingdomFactory {
        @Override
        public King createKing() {
            return new OrcKing();
        }
    }

    enum Type {
        ORC,

        ELF
    }


    static class FactoryProducer {

        private FactoryProducer() {

        }

        public static KingdomFactory getFactory(Type type) {
            return switch (type) {
                case ORC -> new OrcKingdomFactory();
                case ELF -> new ElfKingdomFactory();
                default -> throw new CustomException("Unknown type error");
            };
        }
    }
}
