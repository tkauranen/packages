(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.7.1" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "6.4.0")
(def +version+ (str +lib-version+ "-0"))

(task-options!
  pom  {:project     'cljsjs/elliptic
        :version     +version+
        :description "Fast Elliptic Curve Cryptography in plain javascript"
        :url         "https://github.com/indutny/elliptic"
        :license     {"MIT" "https://github.com/indutny/elliptic#license"}
        :scm         {:url "https://github.com/cljsjs/packages"}})

(deftask package []
  (comp
    (download :url (str "https://wzrd.in/standalone/elliptic@" +lib-version+))
    (sift :move {(re-pattern (str "^elliptic@" +lib-version+)) "cljsjs/elliptic/development/elliptic.inc.js"})
    (minify :in "cljsjs/elliptic/development/elliptic.inc.js"
            :out "cljsjs/elliptic/production/elliptic.min.inc.js")
    (sift :include #{#"^cljsjs"})
    (deps-cljs :name "cljsjs.elliptic")
    (pom)
    (jar)))
