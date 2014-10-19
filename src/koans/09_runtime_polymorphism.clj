(ns koans.09-runtime-polymorphism
  (:require [koan-engine.core :refer :all]))

(defn hello
  ([] "Hello World!")
  ([a] (str "Hello, you silly " a "."))
  ([a & more] (str "Hello to this group: "
                   (apply str
                          (interpose ", " (concat (list a) more)))
                   "!")))

(defmulti diet (fn [x] (:eater x)))
(defmethod diet :herbivore [{:keys [name]}] (str name " eats veggies."))
(defmethod diet :carnivore [{:keys [name]}] (str name " eats animals."))
(defmethod diet :default [{:keys [name]}] (str "I don't know what " name " eats."))

(meditations
  "Some functions can be used in different ways - with no arguments"
  (= "Hello World!" (hello))

  "With one argument"
  (= "Hello, you silly world." (hello "world"))

  "Or with many arguments"
  (= "Hello to this group: Peter, Paul, Mary!"
     (hello "Peter" "Paul" "Mary"))

  "Multimethods allow more complex dispatching"
  (= "Bambi eats veggies."
     (diet {:species "deer" :name "Bambi" :age 1 :eater :herbivore}))

  "Animals have different names"
  (= "Thumper eats veggies."
    (diet {:species "rabbit" :name "Thumper" :age 1 :eater :herbivore}))

  "Different methods are used depending on the dispatch function result"
  (= "Simba eats animals."
     (diet {:species "lion" :name "Simba" :age 1 :eater :carnivore}))

  "You may use a default method when no others match"
  (= "I don't know what Rich Hickey eats."
     (diet {:name "Rich Hickey"})))
