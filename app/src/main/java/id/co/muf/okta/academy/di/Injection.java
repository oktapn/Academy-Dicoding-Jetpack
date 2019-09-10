package id.co.muf.okta.academy.di;

import android.app.Application;

import id.co.muf.okta.academy.data.source.AcademyRepository;
import id.co.muf.okta.academy.data.source.remote.RemoteRepository;
import id.co.muf.okta.academy.utils.JsonHelper;

public class Injection {
    public static AcademyRepository provideRepository(Application application) {

        RemoteRepository remoteRepository = RemoteRepository.getInstance(new JsonHelper(application));

        return AcademyRepository.getInstance(remoteRepository);
    }
}
