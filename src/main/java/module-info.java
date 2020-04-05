/**
 * Created by Kneelawk on 4/4/20.
 */
module com.kneelawk.modpackattributor {
    exports com.kneelawk.modpackattributor;
    exports com.kneelawk.modpackattributor.curse;
    exports com.kneelawk.modpackattributor.data;
    exports com.kneelawk.modpackattributor.data.curseapi;
    exports com.kneelawk.modpackattributor.data.manifest;

    requires kotlin.stdlib;

    requires java.json;

    requires tornadofx;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
}