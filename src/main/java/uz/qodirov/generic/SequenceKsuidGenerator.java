package uz.qodirov.generic;

import com.github.ksuid.Ksuid;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.StringNVarcharType;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class SequenceKsuidGenerator extends SequenceStyleGenerator {
    public static final String CLASS_NAME = "uz.qodirov.generic.SequenceKsuidGenerator";
    public static final String VALUE_PREFIX_PARAMETER = "valuePrefix";
    public static final String VALUE_PREFIX_DEFAULT = "id_";
    private String valuePrefix;

    @Override
    public Serializable generate(SharedSessionContractImplementor session,
                                 Object object) throws HibernateException {
        return valuePrefix + Ksuid.newKsuid().toString();
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(StringNVarcharType.INSTANCE, params, serviceRegistry);
        valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER, params, VALUE_PREFIX_DEFAULT);
    }

}
