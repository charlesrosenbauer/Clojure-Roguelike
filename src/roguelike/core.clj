(ns roguelike.core
  (:gen-class))
(require '[clojure.string :as str])










(defstruct Block :X :Y :Tiles)
(defstruct World :Blocks)










(defn validTile?
  [tile]
  (contains? #{" " "#" "*"} tile))










(defn fillBlock
  "Creates a block, fills it with a specified tile. I should probably optimize this later."
  [x y tile]
  (let [realtile (if (validTile? tile) tile " ")
        row (take 8 (cycle [realtile]))
	      mat (take 8 (cycle [row     ]))]

        (struct Block x y mat)))










(defn showableworld
  "Filters blocks in based on whether or not they are viewable."
  [world x y rx ry]
  (let
    [lox (- x (/ rx 2))
     hix (+ x (/ rx 2))
     loy (- y (/ ry 2))
     hiy (+ y (/ ry 2)) ]
     (filter (fn [block]
                (let [bx (:X block)
                      by (:Y block)]
                     (and (< bx hix) (> bx lox)
                          (< by hiy) (> by loy))) world))))










(defn showblock
  "Transforms a block into a string"
  [block]
  (str/join "\n" (map #(str/join "" %) (:Tiles block))))










(defn -main
  [& args]
  (println (showblock (fillBlock 0 0 "#"))))
