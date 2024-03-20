package com.nags.searchengine.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Component;

@Component
public class PredicateFactory {
    protected PredicateGenerator integerGenerator = new IntegerPredicateGenerator();
    protected PredicateGenerator instantGenerator = new InstantPredicateGenerator();
    protected PredicateGenerator stringGenerator = new StringPredicateGenerator();
    protected PredicateGenerator booleanGenerator = new BooleanPredicateGenerator();
    protected PredicateGenerator characterGenerator = new CharacterPredicateGenerator();
    protected PredicateGenerator longGenerator = new LongPredicateGenerator();
    protected PredicateGenerator objectGenerator = new ObjectPredicateGenerator();

    @SuppressWarnings("rawtypes")
    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
        PredicateGenerator generator = objectGenerator;
        Class entityClass = path.getJavaType();
        if (Integer.class.isAssignableFrom(entityClass) || int.class.isAssignableFrom(entityClass)) {
            generator = integerGenerator;
        } else if (Instant.class.isAssignableFrom(entityClass)) {
            generator = instantGenerator;
        } else if (Boolean.class.isAssignableFrom(entityClass)
                || boolean.class.isAssignableFrom(entityClass)) {
            generator = booleanGenerator;
        } else if (Character.class.isAssignableFrom(entityClass)
                || char.class.isAssignableFrom(entityClass)) {
            generator = characterGenerator;
        } else if (Long.class.isAssignableFrom(entityClass)
                || long.class.isAssignableFrom(entityClass)) {
            generator = longGenerator;
        } else if (String.class.isAssignableFrom(entityClass)) {
            generator = stringGenerator;
        }

        return generator.getPredicate(criteriaBuilder, filter, path);
    }

    protected interface PredicateGenerator {
        @SuppressWarnings("rawtypes")
        Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path);
    }


    protected static class LongPredicateGenerator implements PredicateGenerator {
        @SuppressWarnings({"rawtypes", "unchecked"})
        @Override
        public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
            Predicate result;
            Long val1 = (Long) (filter.getValues().get(0));

            switch (filter.getOperator()) {
                case EQUAL:
                    result = criteriaBuilder.equal(path, val1);
                    break;
                case INF:
                    result = criteriaBuilder.lessThan(path, val1);
                    break;
                case INF_EG:
                    result = criteriaBuilder.lessThanOrEqualTo(path, val1);
                    break;
                case SUP:
                    result = criteriaBuilder.greaterThan(path, val1);
                    break;
                case SUP_EG:
                    result = criteriaBuilder.greaterThanOrEqualTo(path, val1);
                    break;
                case BETWEEN:
                    Long val2 = (Long) (filter.getValues().get(1));
                    result = criteriaBuilder.between(path, val1, val2);
                    break;
                case IN:
                    ArrayList<Long> vals = new ArrayList(filter.getValues().size());
                    for (Object op : filter.getValues()) {
                        vals.add((Long) (op));
                    }
                    result = path.in(vals);
                    break;
                default:
                    List<Object> args = new ArrayList<>();
                    args.add(filter.getOperator().toString());
                    args.add(filter.getColumn());
                    throw new IllegalArgumentException(
                            "Operator " + args.get(0) + "not supported on column " + args.get(1));
            }
            return result;
        }
    }

    protected static class IntegerPredicateGenerator implements PredicateGenerator {
        @SuppressWarnings({"rawtypes", "unchecked"})
        @Override
        public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
            Predicate result;
            Integer val1 = (Integer) (filter.getValues().get(0));

            switch (filter.getOperator()) {
                case EQUAL:
                    result = criteriaBuilder.equal(path, val1);
                    break;
                case INF:
                    result = criteriaBuilder.lessThan(path, val1);
                    break;
                case INF_EG:
                    result = criteriaBuilder.lessThanOrEqualTo(path, val1);
                    break;
                case SUP:
                    result = criteriaBuilder.greaterThan(path, val1);
                    break;
                case SUP_EG:
                    result = criteriaBuilder.greaterThanOrEqualTo(path, val1);
                    break;
                case BETWEEN:
                    Integer val2 = (Integer) (filter.getValues().get(1));
                    result = criteriaBuilder.between(path, val1, val2);
                    break;
                case IN:
                    ArrayList<Integer> vals = new ArrayList(filter.getValues().size());
                    for (Object op : filter.getValues()) {
                        vals.add((Integer) (op));
                    }
                    result = path.in(vals);
                    break;
                default:
                    List<Object> args = new ArrayList<>();
                    args.add(filter.getOperator().toString());
                    args.add(filter.getColumn());
                    throw new IllegalArgumentException(
                            "Operator " + args.get(0) + "not supported on column " + args.get(1));
            }
            return result;
        }
    }

    protected static class BooleanPredicateGenerator implements PredicateGenerator {

        @SuppressWarnings({"rawtypes"})
        @Override
        public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
            Predicate result;
            Boolean val1 = (Boolean) filter.getValues().get(0);

            if (filter.getOperator() == OperationEnum.EQUAL) {
                result = criteriaBuilder.equal(path, val1);
            } else {
                List<Object> args = new ArrayList<>();
                args.add(filter.getOperator().toString());
                args.add(filter.getColumn());
                throw new IllegalArgumentException(
                        "Operator " + args.get(0) + "not supported on column " + args.get(1));
            }
            return result;
        }
    }

    protected static class StringPredicateGenerator implements PredicateGenerator {
        @SuppressWarnings({"rawtypes", "unchecked"})
        @Override
        public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
            Predicate result;
            String val1 = (String) filter.getValues().get(0);

            switch (filter.getOperator()) {
                case EQUAL:
                    result = criteriaBuilder.equal(path, val1);
                    break;
                case INF:
                    result = criteriaBuilder.lessThan(path, val1);
                    break;
                case INF_EG:
                    result = criteriaBuilder.lessThanOrEqualTo(path, val1);
                    break;
                case SUP:
                    result = criteriaBuilder.greaterThan(path, val1);
                    break;
                case SUP_EG:
                    result = criteriaBuilder.greaterThanOrEqualTo(path, val1);
                    break;
                case BETWEEN:
                    result = criteriaBuilder.like(criteriaBuilder.upper(path),
                            criteriaBuilder.upper(criteriaBuilder.literal("%" + val1 + "%")));
                    break;
                case IN:
                    result = path.in(filter.getValues());
                    break;
                case START:
                    result = criteriaBuilder.like(criteriaBuilder.upper(path),
                            criteriaBuilder.upper(criteriaBuilder.literal(val1 + "%")));
                    break;
                default:
                    List<Object> args = new ArrayList<>();
                    args.add(filter.getOperator().toString());
                    args.add(filter.getColumn());
                    throw new IllegalArgumentException(
                            "Operator " + args.get(0) + "not supported on column " + args.get(1));
            }
            return result;
        }
    }

    protected static class ObjectPredicateGenerator implements PredicateGenerator {
        @SuppressWarnings({"rawtypes", "unchecked"})
        @Override
        public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
            if (filter.getOperator() == OperationEnum.EQUAL) {
                return criteriaBuilder.equal(path, filter.getValues().get(0));
            }
            if (filter.getOperator() == OperationEnum.IN) {
                return path.in(filter.getValues());
            }

            List<Object> args = new ArrayList<>();
            args.add(filter.getOperator().toString());
            args.add(filter.getColumn());
            throw new IllegalArgumentException(
                    "Operator " + args.get(0) + "not supported on column " + args.get(1));

        }
    }

    protected static class CharacterPredicateGenerator implements PredicateGenerator {
        @SuppressWarnings({"rawtypes", "unchecked"})
        @Override
        public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
            Predicate result;
            Character val1 = (Character) filter.getValues().get(0);

            switch (filter.getOperator()) {
                case EQUAL:
                    result = criteriaBuilder.equal(path, val1);
                    break;
                case START:
                    ArrayList<Character> vals = new ArrayList(filter.getValues().size());
                    for (Object op : filter.getValues()) {
                        String opString = (String) op;
                        if (opString.length() > 0) {
                            vals.add(opString.charAt(0));
                        }
                    }
                    result = path.in(vals);
                    break;
                default:
                    List<Object> args = new ArrayList<>();
                    args.add(filter.getOperator().toString());
                    args.add(filter.getColumn());
                    throw new IllegalArgumentException(
                            "Operator " + args.get(0) + "not supported " + args.get(1) + " for Char");
            }
            return result;
        }
    }

    protected static class InstantPredicateGenerator implements PredicateGenerator {

        @SuppressWarnings({"rawtypes", "unchecked"})
        @Override
        public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
            Predicate result;
            Instant val1 = (Instant) filter.getValues().get(0);
            switch (filter.getOperator()) {
                case EQUAL:
                    result = criteriaBuilder.equal(path, val1);
                    break;
                case INF:
                    result = criteriaBuilder.lessThan(path, val1);
                    break;
                case INF_EG:
                    result = criteriaBuilder.lessThanOrEqualTo(path, val1);
                    break;
                case SUP:
                    result = criteriaBuilder.greaterThan(path, val1);
                    break;
                case SUP_EG:
                    result = criteriaBuilder.greaterThanOrEqualTo(path, val1);
                    break;
                case BETWEEN:
                    Instant val2 = (Instant) (filter.getValues().get(1));
                    result = criteriaBuilder.between(path, val1, val2);
                    break;

                case IN:
                    List<Instant> vals = new ArrayList(filter.getValues().size());
                    for (Object op : filter.getValues()) {
                        vals.add((Instant) op);
                    }
                    result = path.in(vals);
                    break;
                default:
                    List<Object> args = new ArrayList<>();
                    args.add(filter.getOperator().toString());
                    args.add(filter.getColumn());
                    throw new IllegalArgumentException(
                            "Operator " + args.get(0) + "not supported on column " + args.get(1) + " for Instant");
            }
            return result;
        }
    }
}
