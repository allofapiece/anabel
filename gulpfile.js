let argv         = require('minimist')(process.argv.slice(2));
let gulp         = require('gulp');
let gulpif       = require('gulp-if');
let plumber      = require('gulp-plumber');
let notify       = require('gulp-notify');
let sass         = require('gulp-sass');
let sourcemaps   = require('gulp-sourcemaps');
let autoprefixer = require('gulp-autoprefixer');
let cssnano      = require('gulp-cssnano');
let replace      = require('gulp-replace');
let rename       = require('gulp-rename');
let concat       = require('gulp-concat');
let uglify       = require('gulp-uglify-es').default;
let cache        = require('gulp-cache');
let imagemin     = require('gulp-imagemin');
let pngquant     = require('imagemin-pngquant');
let del          = require('del');

let onError      = function(error) {
    notify.onError({
        title: 'Error!',
        message: '<%= error.message %>'
    })(error);

    this.emit('end');
};

let onComplete   = {
    title: 'Complete!',
    message: '<%= file.relative %>',
    onLast: true
};

let config       = {
    production: argv.production,
    maps: !argv.production
};

const srcDir = 'src/main/assets/';
const destDir = config.production ? 'src/main/resources/static/assets/' : 'target/classes/static/assets/';

gulp.task('sass', function () {
    return gulp.src(srcDir + 'scss/**/*.+(sass|scss)')
        .pipe(gulpif(!config.production, plumber({errorHandler: onError}), plumber()))
        .pipe(gulpif(config.maps, sourcemaps.init()))
        .pipe(sass())
        .pipe(autoprefixer({browsers: ['last 10 versions', '> 1%', 'ie 11', 'android 4', 'opera 12']}))
        .pipe(cssnano({autoprefixer: {browsers: ['last 10 versions', '> 1%', 'ie 11', 'android 4', 'opera 12']}}))
        .pipe(rename({suffix: '.min'}))
        .pipe(gulpif(config.maps, sourcemaps.write('.', {sourceRoot: srcDir + 'scss'})))
        .pipe(gulp.dest(destDir + 'css'))
        .pipe(gulpif(!config.production, notify(onComplete)));
});

gulp.task('css', function () {
    return gulp.src(srcDir + 'css/**/*.css')
        .pipe(gulpif(!config.production, plumber({errorHandler: onError}), plumber()))
        .pipe(gulpif(config.maps, sourcemaps.init()))
        .pipe(cssnano({autoprefixer: {browsers: ['last 10 versions', '> 1%', 'ie 11', 'android 4', 'opera 12']}}))
        .pipe(rename({suffix: '.min'}))
        .pipe(gulpif(config.maps, sourcemaps.write('.', {sourceRoot: srcDir + 'css'})))
        .pipe(gulp.dest(destDir + 'css'))
        .pipe(gulpif(!config.production, notify(onComplete)));
});

gulp.task('fonts', function () {
    return gulp.src([srcDir + 'fonts/**/*', '!assets/fonts/**/*.json'])
        .pipe(gulpif(!config.production, plumber({errorHandler: onError}), plumber()))
        .pipe(gulp.dest(destDir + 'fonts'))
        .pipe(gulpif(!config.production, notify(onComplete)));
});

gulp.task('img', function () {
    return gulp.src([srcDir + 'img/**/*'])
        .pipe(gulpif(!config.production, plumber({errorHandler: onError}), plumber()))
        .pipe(cache(imagemin({
            interlaced: true,
            progressive: true,
            svgoPlugins: [{removeViewBox: false}],
            use: [pngquant()]
        })))
        .pipe(gulp.dest(destDir + 'img'))
        .pipe(gulpif(!config.production, notify(onComplete)));
});

gulp.task('js', function () {
    return gulp.src([srcDir + 'js/**/*.js'])
        .pipe(gulpif(!config.production, plumber({errorHandler: onError}), plumber()))
        .pipe(gulpif(config.maps, sourcemaps.init()))
        .pipe(uglify())
        .pipe(rename({suffix: '.min'}))
        .pipe(gulpif(config.maps, sourcemaps.write('.', {sourceRoot: srcDir + 'js'})))
        .pipe(gulp.dest(destDir + 'js'))
        .pipe(gulpif(!config.production, notify(onComplete)));
});

gulp.task('clean', function() {
    return del.sync([
        destDir + 'css/**/*',
        destDir + 'fonts/**/*',
        destDir + 'img/**/*',
        destDir + 'js/**/*'
    ]);
});

gulp.task('clear', function () {
    return cache.clearAll();
});

gulp.task('build', ['clean'], function () {
    gulp.src(['node_modules/bootstrap/dist/js/bootstrap.bundle.min.*'])
        .pipe(gulp.dest(destDir + 'js'));

    gulp.src(['node_modules/jquery/dist/jquery.min.*'])
        .pipe(gulp.dest(destDir + 'js'));

    gulp.src(['node_modules/jquery-form/dist/jquery.form.min.*'])
        .pipe(gulp.dest(destDir + 'js'));

    gulp.src(['node_modules/jquery.cookie/jquery.cookie.js'])
        .pipe(uglify())
        .pipe(rename({suffix: '.min'}))
        .pipe(gulp.dest(destDir + 'js'));

    gulp.src(['node_modules/inputmask/dist/min/jquery.inputmask.bundle.min.js'])
        .pipe(gulp.dest(destDir + 'js'));

    /**
     * Validation plugin
     */
    gulp.src(['node_modules/jquery-validation/dist/jquery.validate.js'])
        .pipe(uglify())
        .pipe(rename({suffix: '.min'}))
        .pipe(gulp.dest(destDir + 'js/jquery-validation'));
    gulp.src(['node_modules/jquery-validation/dist/localization/messages_*.min.js'])
        .pipe(gulp.dest(destDir + 'js/jquery-validation/localization'));

    gulp.start('sass');
    gulp.start('css');
    gulp.start('img');
    gulp.start('fonts');
    gulp.start('js');
});

gulp.task('watch', ['build'], function () {
    gulp.watch(srcDir + 'scss/**/*.+(sass|scss)', ['sass']);
    gulp.watch(srcDir + 'css/**/*.css', ['css']);
    gulp.watch(srcDir + 'js/**/*.js', ['js']);
});

gulp.task('default', ['watch']);
