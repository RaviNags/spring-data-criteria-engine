# Search-engine

This project has to purpose to do quick search or filters with pagination on your hibernate entities base on hibernate criteria using.

# How to use with a example ?

We have two table in our use case : Enterprise 1 ------ * User

| User |
| ------ |
| id (long) |
| firstname (string) |
| lastname (string) |
| age (int) |

| Enterprise |
| ------ |
| id (long) |
| name (string) |
| number (string) |

Our need is to request dynamically your user table to do a search function with pagination.

### 1 - Create a search repository with the User entity:

```java

@Component
public class UserSearchRepository extends AbstractSearchRepository<User> {

  @SuppressWarnings("rawtypes")
  @Override
  protected Class getEntityClass() {
    return EpTask.class.getClass();
  }

  @Override
  protected Root<User> getRoot(CriteriaQuery<User> query) {
    return query.from(User.class);
  }

}

```

### 2 - Create a search service with the User entity:

```java

@Service
public class UserSearchService extends AbstractSearchService<User> {

  @Autowired
  private UserSearchRepository repo;

  @Override
  public AbstractSearchRepository<User> getSearchRepository() {
    return this.repo;
  }

}

```

### 3 - Call our service

```java
@GetMapping("/user")
public ResponseEntity<Response> getUsers(
      @RequestParam(required = false) String firstname,
      @RequestParam(required = false) String lastname) {
    Search search = new Search();
    GroupedFilter group = new GroupedFilter(RequestTypeEnum.AND);
    search.addGroupedFilter(group);

    if (firstname != null && !"".equals(firstname)) {
      group.addFilter(new Filter("firstname", OperationEnum.START, firstname));
    }

    if (lastname != null) {
      group.addFilter(new Filter("lastname", OperationEnum.START, lastname));
    }

    search.addSort(new Sort("firstname", "ASC"));

    List<User> result = this.userSearchService.find(search);
    return successResponse(result);
  }
```
