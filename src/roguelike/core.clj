(ns roguelike.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))










(defn fillBlock
  "Creates a block, fills it with a specified tile. I should probably optimize this later."
  [tile]
  [[tile tile tile tile]
   [tile tile tile tile]
   [tile tile tile tile]
   [tile tile tile tile]])










(defn showableworld
  "Filters blocks in based on whether or not they are viewable."
  [world x y rx ry]
  (let
    [lox (- x (/ rx 2))
     hix (+ x (/ rx 2))
     loy (- y (/ ry 2))
     hiy (+ y (/ ry 2)) ]
     (filter (fn [bx by block]
                            (and
                             (< bx hix) (> bx lox)
                             (< by hiy) (> by loy))) world)))
