import entity.Account;
import entity.Department;
import entity.Group;
import entity.GroupAccount;
import util.HibernateUtil;



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

                var groupAccount1 = new GroupAccount();
                var pk1 = new GroupAccount.PrimaryKey();
                pk1.setGroupId(1);
                pk1.setAccountId(1);
                groupAccount1.setPk(pk1);
                session.persist(groupAccount1);

                var groupAccount2 = new GroupAccount();
                var pk2 = new GroupAccount.PrimaryKey();
                pk2.setGroupId(1);
                pk2.setAccountId(2);
                groupAccount2.setPk(pk2);
                session.persist(groupAccount2);

                var groupAccount3 = new GroupAccount();
                var pk3 = new GroupAccount.PrimaryKey();
                pk3.setGroupId(2);
                pk3.setAccountId(2);
                groupAccount3.setPk(pk3);
                session.persist(groupAccount3);

                var groupAccount4 = new GroupAccount();
                var pk4 = new GroupAccount.PrimaryKey();
                pk4.setGroupId(2);
                pk4.setAccountId(1);
                groupAccount4.setPk(pk4);
                session.persist(groupAccount4);


            });

            factory.inSession(session -> {
                var hql = "FROM Group";
                var groups = session
                        .createSelectionQuery(hql, Group.class)
                        .getResultList();
                for (var group : groups) {
                    System.out.println("👉 group = " + group.getName());
                    var groupAccounts = group.getGroupAccounts();
                    for (var groupAccount : groupAccounts) {
                        System.out.println("✨ account = " + groupAccount.getAccount().getName());
                        System.out.println("✨ joined at = " + groupAccount.getJoinedAt());
                    }
                }
            });

        }
    }
}
