package id.co.muf.okta.academy.data.source.remote;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import id.co.muf.okta.academy.data.source.remote.response.ContentResponse;
import id.co.muf.okta.academy.data.source.remote.response.CourseResponse;
import id.co.muf.okta.academy.data.source.remote.response.ModuleResponse;
import id.co.muf.okta.academy.utils.EspressoIdlingResource;
import id.co.muf.okta.academy.utils.JsonHelper;

public class RemoteRepository {
    private static RemoteRepository INSTANCE;
    private JsonHelper jsonHelper;
    private final long SERVICE_LATENCY_IN_MILLIS = 2000;

    private RemoteRepository(JsonHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
    }

    public static RemoteRepository getInstance(JsonHelper helper) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteRepository(helper);
        }
        return INSTANCE;
    }

    public LiveData<ApiResponse<List<CourseResponse>>> getAllCoursesAsLiveData() {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<CourseResponse>>> resultCourse = new MutableLiveData<>();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            resultCourse.setValue(ApiResponse.success(jsonHelper.loadCourses()));
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
                EspressoIdlingResource.decrement();
            }
        }, SERVICE_LATENCY_IN_MILLIS);

        return resultCourse;
    }

    public LiveData<ApiResponse<List<ModuleResponse>>> getAllModulesByCourseAsLiveData(String courseId) {

        EspressoIdlingResource.increment();

        MutableLiveData<ApiResponse<List<ModuleResponse>>> resultModules = new MutableLiveData<>();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            resultModules.setValue(ApiResponse.success(jsonHelper.loadModule(courseId)));
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
                EspressoIdlingResource.decrement();
            }
        }, SERVICE_LATENCY_IN_MILLIS);

        return resultModules;
    }

    public LiveData<ApiResponse<ContentResponse>> getContentAsLiveData(String moduleId) {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<ContentResponse>> resultContent = new MutableLiveData<>();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            resultContent.setValue(ApiResponse.success(jsonHelper.loadContent(moduleId)));
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow()) {
                EspressoIdlingResource.decrement();
            }
        }, SERVICE_LATENCY_IN_MILLIS);

        return resultContent;
    }

    public interface LoadCoursesCallback {
        void onAllCoursesReceived(List<CourseResponse> courseResponses);

        void onDataNotAvailable();
    }

    public interface LoadModulesCallback {
        void onAllModulesReceived(List<ModuleResponse> moduleResponses);

        void onDataNotAvailable();
    }

    public interface GetContentCallback {
        void onContentReceived(ContentResponse contentResponse);

        void onDataNotAvailable();
    }

}
