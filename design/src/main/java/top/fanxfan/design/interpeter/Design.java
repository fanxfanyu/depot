package top.fanxfan.design.interpeter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import top.fanxfan.design.exception.CustomException;

import java.util.Deque;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * @author fanxfan
 */
@Slf4j
public class Design {
    public static void main(String[] args) {
        var tokenStr = "4 3 2 - 1 + * 2 /";
        Deque<Expression> stack = new LinkedList<>();
        var tokenSplit = tokenStr.split(" ");
        for (String s : tokenSplit) {
            if (isOperator(s)) {
                var right = stack.pop();
                var left = stack.pop();
                log.info("left:{} {},right {} {}", left, left.interpret(), right, right.interpret());
                var operator = getOperator(s, left, right);
                log.info("operator:{}", operator);
                var result = operator.interpret();
                var numberExpression = new NumberExpression(result);
                stack.push(numberExpression);
                log.info("stack push result:{}", numberExpression.interpret());
            } else {
                var numberExpression = new NumberExpression(s);
                stack.push(numberExpression);
                log.info("stack push numberExpression:{}", numberExpression.interpret());
            }
        }
        log.info("result:{}", stack.pop().interpret());

        var string = "1";
        String regex = "^\\d*$";
        Pattern pattern = Pattern.compile(regex);
        boolean matches = pattern.matcher(string).matches();
        log.info("{}", matches);
        boolean b = pattern.matcher(string).find();
        log.info("{}", b);
    }

    private static Expression getOperator(String s, Expression left, Expression right) {
        Expression expression;
        switch (s) {
            case "+" -> expression = new PlusExpression(left, right);
            case "-" -> expression = new MinusExpression(left, right);
            case "*" -> expression = new MultiplyExpression(left, right);
            case "/" -> expression = new DivisionExpress(left, right);
            default -> throw new CustomException(String.format("%s符号未定义", s));

        }
        return expression;
    }

    public static boolean isOperator(String str) {
        return "+".equals(str) || "-".equals(str) || "*".equals(str) || "/".equals(str);
    }

    @NoArgsConstructor
    abstract static class Expression {
        public abstract int interpret();

        @Override
        public abstract String toString();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class PlusExpression extends Expression {
        private Expression left;
        private Expression right;

        @Override
        public int interpret() {
            return left.interpret() + right.interpret();
        }

        @Override
        public String toString() {
            return "PlusExpression";
        }
    }

    @Getter
    @Setter
    static class NumberExpression extends Expression {
        private int number;

        public NumberExpression(int number) {
            this.number = number;
        }

        public NumberExpression(String s) {
            this.number = Integer.parseInt(s);
        }

        @Override
        public int interpret() {
            return number;
        }

        @Override
        public String toString() {
            return "NumberExpression";
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class MultiplyExpression extends Expression {
        private Expression left;

        private Expression right;

        @Override
        public int interpret() {
            return left.interpret() * right.interpret();
        }

        @Override
        public String toString() {
            return "MultiplyExpression";
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    static class MinusExpression extends Expression {
        private Expression left;

        private Expression right;

        @Override
        public int interpret() {
            return left.interpret() - right.interpret();
        }

        @Override
        public String toString() {
            return "MinusExpression";
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class DivisionExpress extends Expression {
        private Expression left;

        private Expression right;

        @Override
        public int interpret() {
            if (right.interpret() == 0) {
                throw new CustomException("除数不能为0");
            }
            return left.interpret() / right.interpret();
        }

        @Override
        public String toString() {
            return "DivisionExpress";
        }
    }


}
