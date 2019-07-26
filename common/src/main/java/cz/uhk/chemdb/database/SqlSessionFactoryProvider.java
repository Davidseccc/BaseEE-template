package cz.uhk.chemdb.database;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.cdi.SessionFactoryProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.io.Reader;

@ApplicationScoped
public class SqlSessionFactoryProvider {

    @Produces
    @ApplicationScoped
    @SessionFactoryProvider
    public SqlSessionFactory produceFactory() throws IOException {
        Reader configurationReader = Resources.getResourceAsReader("mybatis-configuration.xml");
        return new SqlSessionFactoryBuilder().build(configurationReader);
    }

}
