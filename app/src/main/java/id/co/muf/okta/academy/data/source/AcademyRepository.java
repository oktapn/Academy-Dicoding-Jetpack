package id.co.muf.okta.academy.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import id.co.muf.okta.academy.data.ContentEntity;
import id.co.muf.okta.academy.data.CourseEntity;
import id.co.muf.okta.academy.data.ModuleEntity;
import id.co.muf.okta.academy.data.source.remote.RemoteRepository;
import id.co.muf.okta.academy.data.source.remote.response.ContentResponse;
import id.co.muf.okta.academy.data.source.remote.response.CourseResponse;
import id.co.muf.okta.academy.data.source.remote.response.ModuleResponse;

public class AcademyRepository implements AcademyDataSource{

    private volatile static AcademyRepository INSTANCE = null;

    private final RemoteRepository remoteRepository;

    private AcademyRepository(@NonNull RemoteRepository remoteRepository) {
        this.remoteRepository = remoteRepository;
    }

    public static AcademyRepository getInstance(RemoteRepository remoteData) {
        if (INSTANCE == null) {
            synchronized (AcademyRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AcademyRepository(remoteData);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<CourseEntity>> getAllCourses() {
        MutableLiveData<List<CourseEntity>> courseResults = new MutableLiveData<>();

        remoteRepository.getAllCourses(new RemoteRepository.LoadCoursesCallback() {
            @Override
            public void onAllCoursesReceived(List<CourseResponse> courseResponses) {
                ArrayList<CourseEntity> courseList = new ArrayList<>();
                for (int i = 0; i < courseResponses.size(); i++) {
                    CourseResponse response = courseResponses.get(i);
                    CourseEntity course = new CourseEntity(response.getId(),
                            response.getTitle(),
                            response.getDescription(),
                            response.getDate(),
                            false,
                            response.getImagePath());

                    courseList.add(course);
                }
                courseResults.postValue(courseList);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        return courseResults;
    }

    @Override
    public LiveData<CourseEntity> getCourseWithModules(final String courseId) {
        MutableLiveData<CourseEntity> courseResult = new MutableLiveData<>();

        remoteRepository.getAllCourses(new RemoteRepository.LoadCoursesCallback() {
            @Override
            public void onAllCoursesReceived(List<CourseResponse> courseResponses) {
                for (int i = 0; i < courseResponses.size(); i++) {
                    CourseResponse response = courseResponses.get(i);
                    if (response.getId().equals(courseId)) {
                        CourseEntity course = new CourseEntity(response.getId(),
                                response.getTitle(),
                                response.getDescription(),
                                response.getDate(),
                                false,
                                response.getImagePath());
                        courseResult.postValue(course);
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        return courseResult;
    }

    @Override
    public LiveData<List<CourseEntity>> getBookmarkedCourses() {
        MutableLiveData<List<CourseEntity>> courseResults = new MutableLiveData<>();

        remoteRepository.getAllCourses(new RemoteRepository.LoadCoursesCallback() {
            @Override
            public void onAllCoursesReceived(List<CourseResponse> courseResponses) {
                ArrayList<CourseEntity> courseList = new ArrayList<>();
                for (int i = 0; i < courseResponses.size(); i++) {
                    CourseResponse response = courseResponses.get(i);
                    CourseEntity course = new CourseEntity(response.getId(),
                            response.getTitle(),
                            response.getDescription(),
                            response.getDate(),
                            false,
                            response.getImagePath());
                    courseList.add(course);
                }
                courseResults.postValue(courseList);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        return courseResults;
    }

    @Override
    public LiveData<List<ModuleEntity>> getAllModulesByCourse(String courseId) {
        MutableLiveData<List<ModuleEntity>> moduleResults= new MutableLiveData<>();

        remoteRepository.getModules(courseId, new RemoteRepository.LoadModulesCallback() {
            @Override
            public void onAllModulesReceived(List<ModuleResponse> moduleResponses) {
                ArrayList<ModuleEntity> moduleList = new ArrayList<>();
                for (int i = 0; i < moduleResponses.size(); i++) {
                    ModuleResponse response = moduleResponses.get(i);
                    ModuleEntity course = new ModuleEntity(response.getModuleId(),
                            response.getCourseId(),
                            response.getTitle(),
                            response.getPosition(),
                            false);

                    moduleList.add(course);
                }
                moduleResults.postValue(moduleList);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });


        return moduleResults;
    }

    @Override
    public LiveData<ModuleEntity> getContent(String courseId, String moduleId) {
        MutableLiveData<ModuleEntity> moduleResult = new MutableLiveData<>();
        remoteRepository.getModules(courseId, new RemoteRepository.LoadModulesCallback() {
            @Override
            public void onAllModulesReceived(List<ModuleResponse> moduleResponses) {
                ModuleEntity module;
                for (int i = 0; i < moduleResponses.size(); i++) {
                    ModuleResponse moduleResponse = moduleResponses.get(i);

                    String id = moduleResponse.getModuleId();

                    if (id.equals(moduleId)) {
                        module = new ModuleEntity(id, moduleResponse.getCourseId(), moduleResponse.getTitle(), moduleResponse.getPosition(), false);

                        remoteRepository.getContent(moduleId, new RemoteRepository.GetContentCallback() {
                            @Override
                            public void onContentReceived(ContentResponse contentResponse) {
                                module.contentEntity = new ContentEntity(contentResponse.getContent());
                                moduleResult.postValue(module);
                            }

                            @Override
                            public void onDataNotAvailable() {

                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        return moduleResult;
    }
}
