import entity.Account;
import entity.Department;
import entity.Group;
import entity.GroupAccount;
import util.HibernateUtil;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Program {
    public static void main(String[] args) {
        try (var factory = HibernateUtil.buildSessionFactory()) {
            factory.inTransaction(session -> {
              var group1 = new Group();
              group1.setName("Hibernate Core");
              session.persist(group1);

                var group2 = new Group();
                group2.setName("Spring Boot");
                session.persist(group2);

              var account1 = new Account();
              account1.setName("Long");
              account1.setEmail("long@gmail.com");
              session.persist(account1);

                var account2= new Account();
                account2.setName("Thảo");
                account2.setEmail("thảo@gmail.com");
                session.persist(account2);
                
                account1.setGroups(Arrays.asList(group1,group2));
                account2.setGroups(Arrays.asList(group1,group2));
                group1.setAccounts(Arrays.asList(account2,account1));
                group2.setAccounts(Arrays.asList(account1,account2));

                session.persist(group1);
                session.persist(group2);
            });

            factory.inSession(session -> {
                var hql = "FROM Group";
                var groups = session
                        .createSelectionQuery(hql, Group.class)
                        .getResultList();
                for (var group : groups) {
                    System.out.println("👉 group = " + group.getName());
                    var accounts = group.getAccounts();
                    for( var account : accounts) {
                        System.out.println("✨ account = " + account.getName());
                    }
                }
            });

        }
    }
}
