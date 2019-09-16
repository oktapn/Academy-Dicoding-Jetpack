package id.co.muf.okta.academy.ui.academy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.co.muf.okta.academy.data.source.local.entity.CourseEntity;
import id.co.muf.okta.academy.data.source.AcademyRepository;
import id.co.muf.okta.academy.vo.Resource;

public class AcademyViewModel extends ViewModel {

    private AcademyRepository academyRepository;

    private MutableLiveData<String> mLogin = new MutableLiveData<>();

    public AcademyViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    LiveData<Resource<List<CourseEntity>>> courses = Transformations.switchMap(mLogin,
            data -> academyRepository.getAllCourses());

    void setUsername(String username) {
        mLogin.setValue(username);
    }

}
