package com.nags.searchengine.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import org.springframework.stereotype.Component;

@Component
public class PredicateFactory {
  protected PredicateGenerator integerGenerator = new IntegerPredicateGenerator();
  protected PredicateGenerator dateGenerator = new DatePredicateGenerator();
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
    } else if (Date.class.isAssignableFrom(entityClass)
        || Calendar.class.isAssignableFrom(entityClass)) {
      generator = dateGenerator;
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

    Predicate result = generator.getPredicate(criteriaBuilder, filter, path);
    return result;
  }

  protected interface PredicateGenerator {
    @SuppressWarnings("rawtypes")
    Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path);
  }


  protected class LongPredicateGenerator implements PredicateGenerator {
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
      Predicate result = null;
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
          ArrayList<Long> vals = new ArrayList<Long>(filter.getValues().size());
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
              "Opérateur " + args.get(0) + "non supporté pour la colonne " + args.get(1));
      }
      return result;
    }
  }

  protected class IntegerPredicateGenerator implements PredicateGenerator {
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
      Predicate result = null;
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
          ArrayList<Integer> vals = new ArrayList<Integer>(filter.getValues().size());
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
              "Opérateur " + args.get(0) + "non supporté pour la colonne " + args.get(1));
      }
      return result;
    }
  }

  protected class BooleanPredicateGenerator implements PredicateGenerator {

    @SuppressWarnings({"rawtypes"})
    @Override
    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
      Predicate result = null;
      Boolean val1 = (Boolean) filter.getValues().get(0);

      switch (filter.getOperator()) {
        case EQUAL:
          result = criteriaBuilder.equal(path, val1);
          break;
        default:
          List<Object> args = new ArrayList<>();
          args.add(filter.getOperator().toString());
          args.add(filter.getColumn());
          throw new IllegalArgumentException(
              "Opérateur " + args.get(0) + "non supporté pour la colonne " + args.get(1));
      }
      return result;
    }
  }

  protected class StringPredicateGenerator implements PredicateGenerator {
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
      Predicate result = null;
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
          String val2 = (String) filter.getValues().get(1);
          result = criteriaBuilder.between(path, val1, val2);
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
              "Opérateur " + args.get(0) + "non supporté pour la colonne " + args.get(1));
      }
      return result;
    }
  }

  protected class ObjectPredicateGenerator implements PredicateGenerator {
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
      Predicate result = null;
      Object val1 = filter.getValues().get(0);

      switch (filter.getOperator()) {
        case EQUAL:
          result = criteriaBuilder.equal(path, val1);
          break;
        default:
          List<Object> args = new ArrayList<>();
          args.add(filter.getOperator().toString());
          args.add(filter.getColumn());
          throw new IllegalArgumentException(
              "Opérateur " + args.get(0) + "non supporté pour la colonne " + args.get(1));
      }
      return result;
    }
  }

  protected class CharacterPredicateGenerator implements PredicateGenerator {
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
      Predicate result = null;
      Character val1 = (Character) filter.getValues().get(0); // .charAt(0);

      switch (filter.getOperator()) {
        case EQUAL:
          result = criteriaBuilder.equal(path, val1);
          break;
        case START:
          ArrayList<Character> vals = new ArrayList<Character>(filter.getValues().size());
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
              "Opérateur " + args.get(0) + "non supporté pour la colonne " + args.get(1));
      }
      return result;
    }
  }

  protected class DatePredicateGenerator implements PredicateGenerator {
    protected Date finDeJournee(Date date) {
      GregorianCalendar cal = new GregorianCalendar();
      cal.setTime(date);
      cal.set(Calendar.HOUR, 23);
      cal.set(Calendar.MINUTE, 59);
      cal.set(Calendar.SECOND, 59);
      return cal.getTime();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Filter filter, Path path) {
      Predicate result = null;
      Date val1 = new Date();
      val1.setTime((Long) filter.getValues().get(0));

      switch (filter.getOperator()) {
        case EQUAL:
          result = criteriaBuilder.equal(path, val1);
          break;
        case INF:
          result = criteriaBuilder.lessThan(path, val1);
          break;
        case INF_EG:
          // On construit la date de fin de journée...
          result = criteriaBuilder.lessThanOrEqualTo(path, val1);
          break;
        case SUP:
          result = criteriaBuilder.greaterThan(path, val1);
          break;
        case SUP_EG:
          result = criteriaBuilder.greaterThanOrEqualTo(path, finDeJournee(val1));
          break;
        case BETWEEN:
          Date val2 = new Date();
          val2.setTime((Long) (filter.getValues().get(1)));
          result = criteriaBuilder.between(path, val1, finDeJournee(val2));
          break;
        // Utile???
        case IN:
          ArrayList<Date> vals = new ArrayList<Date>(filter.getValues().size());
          for (Object op : filter.getValues()) {
            Date val = new Date();
            val.setTime((Long) (op));
            vals.add(val);
          }
          result = path.in(vals);
          break;
        default:
          List<Object> args = new ArrayList<>();
          args.add(filter.getOperator().toString());
          args.add(filter.getColumn());
          throw new IllegalArgumentException(
              "Opérateur " + args.get(0) + "non supporté pour la colonne " + args.get(1));
      }
      return result;
    }
  }
}
