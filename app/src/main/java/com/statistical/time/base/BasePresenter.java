/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.statistical.time.base;


import com.statistical.time.interfaces.IModel;
import com.statistical.time.interfaces.IPresenter;
import com.statistical.time.interfaces.IView;

/**
 * @version 1.0 2016/5/19
 */
public class BasePresenter<M extends IModel,V extends IView> implements IPresenter {

    protected M mModel;
    protected V mView;

    public BasePresenter(M model, V rootView) {
        this.mModel = model;
        this.mView = rootView;
//        onStart();
    }

    public BasePresenter(V rootView) {
        this.mView = rootView;
//        onStart();
    }

    public BasePresenter() {
//        onStart();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        if (mModel != null) {
            this.mModel = null;
        }
        this.mView = null;
//        LocationUtil.closeLocation();
    }
}
