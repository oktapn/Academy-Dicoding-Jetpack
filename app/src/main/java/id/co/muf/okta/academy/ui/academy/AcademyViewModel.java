package id.co.muf.okta.academy.ui.academy;

import androidx.lifecycle.ViewModel;

import java.util.List;

import id.co.muf.okta.academy.data.CourseEntity;
import id.co.muf.okta.academy.utils.DataDummy;

public class AcademyViewModel extends ViewModel {

    public List<CourseEntity> getCourses() {
        return DataDummy.generateDummyCourses();
    }

}
