package bav.astro.kmp.shared.planets

import bav.astro.kmp.shared.database.Person
import bav.astro.kmp.shared.ui.birthdays.PersonBirthdayData
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlinx.datetime.yearsUntil
import kotlin.math.ceil
import kotlin.time.Clock

object PlanetUtils {

    /**
     * Returns list of birthdays. Example:
     * ```
     * // take first 100 birthdays ordered by [PersonBirthdayData.nearestBirthday]
     * getBirthdays(persons, start = 0, size = 100)
     *
     * // take next 50 birthdays ordered by [PersonBirthdayData.nearestBirthday]
     * getBirthdays(persons, start = 100, size = 50)
     * ```
     *
     * @param persons List of persons
     * @param start Start index
     * @param size Amount of entities returned
     */
    fun getBirthdays(
        persons: List<Person>,
        planets: List<Planet>,
        start: Int = 0,
        size: Int = 100,
    ) : List<PersonBirthdayData> {
        // 1. Take planet with the smallest period
        // 2. Take first person
        // 3. Take [size] nearest birthdays for the first person on this planet -> [1P]
        // 4. Take second person
        // 5. Take [size] nearest birthdays for the second person on this planet -> [2P]
        // 6. Combine [1P] and [2P] sorting by date, take first [size] elements
        // 7. Take planet with the second-smallest period and repeat steps 2..6

        var result: List<PersonBirthdayData> = emptyList()

        // Make sure that planets list is ordered by period from smallest to largest
        planets.forEach { planet ->
            persons.forEach { person ->
                val nearestBirthdays = getNearestBirthdays(
                    personBirthday = LocalDate.parse(person.birthday),
                    isItEarth = planet.type == PlanetType.EARTH,
                    period = planet.period,
                    size = size,
                )
                val personData = nearestBirthdays.map { birthday ->
                    PersonBirthdayData(
                        name = person.name,
                        planetType = planet.type,
                        planetName = planet.name,
                        ageOnNextBirthday = birthday.ageOnNextBirthday,
                        birthday = birthday.nearestBirthday,
                    )
                }
                result = (result + personData)
                    .sortedBy { it.birthday }
                    .take(size)
            }
        }

        return result
    }

    /**
     * @param personBirthday Person's birthday
     * @param isItEarth Is it earth or not, because for the Earth we calculate differently
     * @param period Period in Earth days
     * @param size Requested size of returning list
     */
    fun getNearestBirthdays(
        personBirthday: LocalDate,
        isItEarth: Boolean,
        period: Double,
        size: Int,
    ) : List<NearestBirthdayData> {
        val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
        if (isItEarth) {
            val currentAmountOfYears = personBirthday.yearsUntil(today)
            return buildList {
                for (i in 1..size) {
                    add(
                        NearestBirthdayData(
                            nearestBirthday = personBirthday
                                .plus(currentAmountOfYears + i, DateTimeUnit.YEAR),
                            ageOnNextBirthday = currentAmountOfYears + 1,
                        )
                    )
                }
            }
        } else {
            val userAgeInEarthDays = if (personBirthday == today) {
                period
            } else {
                personBirthday.daysUntil(today).toDouble()
            }
            val currentYearsOnPlanet = if (personBirthday == today) {
                0.01
            } else {
                userAgeInEarthDays / period
            }
            val firstNextBirthday = personBirthday.plus(
                value = ceil(period * ceil(currentYearsOnPlanet)).toLong(),
                unit = DateTimeUnit.DAY
            )
            val ageOnFirstNextBirthday = currentYearsOnPlanet.toInt() + 1
            return buildList {
                add(
                    NearestBirthdayData(
                        nearestBirthday = firstNextBirthday,
                        ageOnNextBirthday = ageOnFirstNextBirthday,
                    )
                )
                if (size > 1) {
                    for (i in 1 until size) {
                        val nextBirthday = firstNextBirthday.plus(
                            value = ceil(period * i.toDouble()).toLong(),
                            unit = DateTimeUnit.DAY
                        )
                        add(
                            NearestBirthdayData(
                                nearestBirthday = nextBirthday,
                                ageOnNextBirthday = ageOnFirstNextBirthday + i,
                            )
                        )
                    }
                }
            }
        }
    }
}

data class NearestBirthdayData(
    val nearestBirthday: LocalDate,
    val ageOnNextBirthday: Int,
)
