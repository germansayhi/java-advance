import entity.Department;
import entity.GroupAccount;
import util.HibernateUtil;

public class Program {
    public static void main(String[] args) {
        try (var factory = HibernateUtil.buildSessionFactory()) {
            factory.inTransaction(session -> {
               var groupAccount = new GroupAccount();
               var pk = new GroupAccount.PrimaryKey();
               pk.setAccountId(4);
               pk.setGroupId(1);
               groupAccount.setPk(pk);
               session.persist(groupAccount);
            });
            factory.inTransaction(session -> {
                var groupAccount = new GroupAccount();
                var pk = new GroupAccount.PrimaryKey();
                pk.setAccountId(9);
                pk.setGroupId(7);
                groupAccount.setPk(pk);
                session.persist(groupAccount);
            });

            factory.inSession(session -> {
                var hql = "FROM GroupAccount";
                var GroupAccounts = session
                        .createSelectionQuery(hql, GroupAccount.class)
                        .getResultList();
                for (var groupAccount : GroupAccounts) {
                    System.out.println("👌Group Account = " + groupAccount);
                }
            });
        }
    }
}
