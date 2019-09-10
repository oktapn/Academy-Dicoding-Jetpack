package id.co.muf.okta.academy.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import id.co.muf.okta.academy.data.source.AcademyRepository;
import id.co.muf.okta.academy.di.Injection;
import id.co.muf.okta.academy.ui.academy.AcademyViewModel;
import id.co.muf.okta.academy.ui.bookmark.BookmarkViewModel;
import id.co.muf.okta.academy.ui.detail.DetailCourseViewModel;
import id.co.muf.okta.academy.ui.reader.CourseReaderViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;

    private final AcademyRepository mAcademyRepository;

    private ViewModelFactory(AcademyRepository academyRepository) {
        mAcademyRepository = academyRepository;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(Injection.provideRepository(application));
                }
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(AcademyViewModel.class)) {
            //noinspection unchecked
            return (T) new AcademyViewModel(mAcademyRepository);
        } else if (modelClass.isAssignableFrom(DetailCourseViewModel.class)) {
            //noinspection unchecked
            return (T) new DetailCourseViewModel(mAcademyRepository);
        } else if (modelClass.isAssignableFrom(BookmarkViewModel.class)) {
            //noinspection unchecked
            return (T) new BookmarkViewModel(mAcademyRepository);
        } else if (modelClass.isAssignableFrom(CourseReaderViewModel.class)) {
            //noinspection unchecked
            return (T) new CourseReaderViewModel(mAcademyRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
