(set-env!
 :dependencies '[[adzerk/boot-cljs          "1.7.228-2"]
                 [adzerk/boot-cljs-repl     "0.3.0"]
                 [adzerk/boot-reload        "0.4.13"]
                 [hoplon/hoplon             "6.0.0-alpha17"]
                 [hoplon/brew               "7.2.0-SNAPSHOT"]
                 [org.clojure/clojure       "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]
                 [tailrecursion/boot-jetty  "0.1.3"]
                 [com.cemerick/piggieback   "0.2.1"]
                 [weasel                    "0.7.0"]
                 [org.clojure/tools.nrepl   "0.2.13"]
                 [nrepl                     "0.6.0"]]
 :source-paths #{"src"}
 :asset-paths  #{"assets"})

(require
 '[adzerk.boot-cljs         :refer [cljs]]
 '[adzerk.boot-cljs-repl    :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload       :refer [reload]]
 '[hoplon.boot-hoplon       :refer [hoplon prerender]]
 '[tailrecursion.boot-jetty :refer [serve]])

(deftask dev
  "Build weights for local development."
  []
  (comp
    (watch)
    (speak)
    (hoplon)
    (reload)
    (cljs-repl)
    (cljs :source-map true :optimizations :none)
    (serve :port 8000 :init-params {"org.eclipse.jetty.servlet.Default.useFileMappedBuffer" "false"})))

(deftask prod
  "Build weights for production deployment."
  []
  (comp
    (hoplon)
    (cljs :optimizations :advanced)
    (target :dir #{"target"})))
