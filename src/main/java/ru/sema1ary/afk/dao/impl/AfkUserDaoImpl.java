package ru.sema1ary.afk.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import lombok.NonNull;
import ru.sema1ary.afk.dao.AfkUserDao;
import ru.sema1ary.afk.model.AfkUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public class AfkUserDaoImpl extends BaseDaoImpl<AfkUser, Long> implements AfkUserDao {
    public AfkUserDaoImpl(ConnectionSource connectionSource, Class<AfkUser> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public AfkUser save(@NonNull AfkUser user) throws SQLException {
        createOrUpdate(user);
        return user;
    }

    @Override
    public void saveAll(@NonNull List<AfkUser> list) throws SQLException {
        callBatchTasks((Callable<Void>) () -> {
            for (AfkUser user : list) {
                createOrUpdate(user);
            }
            return null;
        });
    }

    @Override
    public Optional<AfkUser> findById(Long aLong) throws SQLException {
        AfkUser result = queryForId(aLong);
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<AfkUser> findByUsername(@NonNull String s) throws SQLException {
        QueryBuilder<AfkUser, Long> queryBuilder = queryBuilder();
        Where<AfkUser, Long> where = queryBuilder.where();
        String columnName = "username";

        SelectArg selectArg = new SelectArg(SqlType.STRING, s.toLowerCase());
        where.raw("LOWER(" + columnName + ")" + " = LOWER(?)", selectArg);
        return Optional.ofNullable(queryBuilder.queryForFirst());
    }

    @Override
    public List<AfkUser> findAll() throws SQLException {
        return queryForAll();
    }
}
