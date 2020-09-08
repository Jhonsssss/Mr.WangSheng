package com.statistical.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.statistical.time.bean.BirdayInfo;
import com.statistical.time.bean.EventInfo;
import com.statistical.time.bean.RiLiEntity;
import com.statistical.time.bean.WishInfo;
import com.statistical.time.bean.CityInfo;

import com.statistical.greendao.BirdayInfoDao;
import com.statistical.greendao.EventInfoDao;
import com.statistical.greendao.RiLiEntityDao;
import com.statistical.greendao.WishInfoDao;
import com.statistical.greendao.CityInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig birdayInfoDaoConfig;
    private final DaoConfig eventInfoDaoConfig;
    private final DaoConfig riLiEntityDaoConfig;
    private final DaoConfig wishInfoDaoConfig;
    private final DaoConfig cityInfoDaoConfig;

    private final BirdayInfoDao birdayInfoDao;
    private final EventInfoDao eventInfoDao;
    private final RiLiEntityDao riLiEntityDao;
    private final WishInfoDao wishInfoDao;
    private final CityInfoDao cityInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        birdayInfoDaoConfig = daoConfigMap.get(BirdayInfoDao.class).clone();
        birdayInfoDaoConfig.initIdentityScope(type);

        eventInfoDaoConfig = daoConfigMap.get(EventInfoDao.class).clone();
        eventInfoDaoConfig.initIdentityScope(type);

        riLiEntityDaoConfig = daoConfigMap.get(RiLiEntityDao.class).clone();
        riLiEntityDaoConfig.initIdentityScope(type);

        wishInfoDaoConfig = daoConfigMap.get(WishInfoDao.class).clone();
        wishInfoDaoConfig.initIdentityScope(type);

        cityInfoDaoConfig = daoConfigMap.get(CityInfoDao.class).clone();
        cityInfoDaoConfig.initIdentityScope(type);

        birdayInfoDao = new BirdayInfoDao(birdayInfoDaoConfig, this);
        eventInfoDao = new EventInfoDao(eventInfoDaoConfig, this);
        riLiEntityDao = new RiLiEntityDao(riLiEntityDaoConfig, this);
        wishInfoDao = new WishInfoDao(wishInfoDaoConfig, this);
        cityInfoDao = new CityInfoDao(cityInfoDaoConfig, this);

        registerDao(BirdayInfo.class, birdayInfoDao);
        registerDao(EventInfo.class, eventInfoDao);
        registerDao(RiLiEntity.class, riLiEntityDao);
        registerDao(WishInfo.class, wishInfoDao);
        registerDao(CityInfo.class, cityInfoDao);
    }
    
    public void clear() {
        birdayInfoDaoConfig.clearIdentityScope();
        eventInfoDaoConfig.clearIdentityScope();
        riLiEntityDaoConfig.clearIdentityScope();
        wishInfoDaoConfig.clearIdentityScope();
        cityInfoDaoConfig.clearIdentityScope();
    }

    public BirdayInfoDao getBirdayInfoDao() {
        return birdayInfoDao;
    }

    public EventInfoDao getEventInfoDao() {
        return eventInfoDao;
    }

    public RiLiEntityDao getRiLiEntityDao() {
        return riLiEntityDao;
    }

    public WishInfoDao getWishInfoDao() {
        return wishInfoDao;
    }

    public CityInfoDao getCityInfoDao() {
        return cityInfoDao;
    }

}
